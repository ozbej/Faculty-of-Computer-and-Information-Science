"""An example of a simple HTTP server."""
import json
import mimetypes
import pickle
import socket
from os.path import isdir
from urllib.parse import unquote_plus


# Exceptions
class MethodError(Exception):
    pass


class BadRequestError(Exception):
    pass


class Redirect(Exception):
    pass


# Pickle file for storing data
PICKLE_DB = "db.pkl"

# Directory containing www data
WWW_DATA = "www-data"

# Header template for a successful HTTP request
HEADER_RESPONSE_200 = """HTTP/1.1 200 OK\r
content-type: %s\r
content-length: %d\r
connection: Close\r
\r
"""

# Represents a table row that holds user data
TABLE_ROW = """
<tr>
    <td>%d</td>
    <td>%s</td>
    <td>%s</td>
</tr>
"""

# Template for a 301 (Moved Permanently) response
RESPONSE_301 = """HTTP/1.1 301 Moved Permanently\r
location: %s\r
content-Type: text/html\r
\r
<!doctype html>
<h1>301 Moved Permanently</h1>
<p>URL redirection</p>
"""

RESPONSE_400 = """HTTP/1.1 400 Bad Request\r
content-type: text/html\r
connection: Closed\r
\r
<!doctype html>
<h1>400 Bad Request</h1>
<p>You sent a request that this server does not understand</p>
"""

# Template for a 404 (Not found) error
RESPONSE_404 = """HTTP/1.1 404 Not found\r
content-type: text/html\r
connection: Close\r
\r
<!doctype html>
<h1>404 Page not found</h1>
<p>Page cannot be found.</p>
"""

RESPONSE_405 = """HTTP/1.1 405 Method Not Allowed\r
content-Type: text/html\r
allow: GET, POST\r
\r
<!doctype html>
<h1>405 Method Not Allowed</h1>
<p>Only GET and POST methods allowed</p>
"""


def save_to_db(first, last):
    """Create a new user with given first and last name and store it into
    file-based database.

    For instance, save_to_db("Mick", "Jagger"), will create a new user
    "Mick Jagger" and also assign him a unique number.

    Do not modify this method."""

    existing = read_from_db()
    existing.append({
        "number": 1 if len(existing) == 0 else existing[-1]["number"] + 1,
        "first": first,
        "last": last
    })
    with open(PICKLE_DB, "wb") as handle:
        pickle.dump(existing, handle)


def read_from_db(criteria=None):
    """Read entries from the file-based DB subject to provided criteria

    Use this method to get users from the DB. The criteria parameters should
    either be omitted (returns all users) or be a dict that represents a query
    filter. For instance:
    - read_from_db({"number": 1}) will return a list of users with number 1
    - read_from_db({"first": "bob"}) will return a list of users whose first
    name is "bob".

    Do not modify this method."""
    if criteria is None:
        criteria = {}
    else:
        # remove empty criteria values
        for key in ("number", "first", "last"):
            if key in criteria and criteria[key] == "":
                del criteria[key]

        # cast number to int
        if "number" in criteria:
            criteria["number"] = int(criteria["number"])

    try:
        with open(PICKLE_DB, "rb") as handle:
            data = pickle.load(handle)

        filtered = []
        for entry in data:
            predicate = True

            for key, val in criteria.items():
                if val != entry[key]:
                    predicate = False

            if predicate:
                filtered.append(entry)

        return filtered
    except (IOError, EOFError):
        return []


def parse_headers_get(client):
    headers = dict()
    while True:
        line = client.readline().decode("utf-8").strip()
        if not line:
            return headers
        key, value = line.split(":", 1)
        headers[key.strip()] = value.strip()


def parse_headers_post_2(client):
    headers = dict()
    while True:
        line = client.readline().decode("utf-8").strip()
        if not line:
            return headers, client.readline(int(headers["Content-Length"])).decode("utf-8").strip()
        key, value = line.split(":", 1)
        headers[key.strip()] = value.strip()


def parse_headers_post(client):
    headers = dict()
    parameters = dict()
    while True:
        line = client.readline().decode("utf-8").strip()
        if not line:
            break
        key, value = line.split(":", 1)
        headers[key.strip()] = value.strip()
    params = unquote_plus(client.readline().decode("utf-8").strip()).split('&')
    for p in params:
        key, value = p.split('=', 1)
        parameters[unquote_plus(key.strip())] = unquote_plus(value.strip())
    return headers, parameters


def check_fields(headers, method):
    host = False
    content_length = False
    for key in headers.keys():
        if key.upper() == "HOST":
            host = True
        if key.upper() == "CONTENT-LENGTH":
            content_length = True
    if not host:
        return False
    if method == "POST" and not content_length:
        return False
    return True


def process_request(connection, address):
    """Process an incoming socket request.

    :param connection is a socket of the client
    :param address is a 2-tuple (address(str), port(int)) of the client
    """

    # Read and parse the request line
    client = connection.makefile("wrb")
    line = client.readline().decode("utf-8").strip()

    try:
        method, uri, version = line.split()

        # Check that method is GET or POST
        method = method.upper()
        if not (method == "GET" or method == "POST"):
            raise MethodError
        if not len(uri) > 0 and not uri[0]:
            raise BadRequestError

        # Check that version is HTTP/1.1
        if not version == "HTTP/1.1":
            raise BadRequestError

        # Read and parse headers
        parameters = dict()
        if method == "GET":
            headers = parse_headers_get(client)
            if len(uri.strip().split('?')) > 1:
                params = unquote_plus(uri.strip()).split('?')[1].split('&')
                uri = unquote_plus(uri.strip()).split('?')[0]
                for p in params:
                    key, value = p.split('=', 1)
                    parameters[key.strip()] = value.strip()
        elif method == "POST":
            headers, params = parse_headers_post_2(client)
            params = params.split('&')
            for p in params:
                key, value = p.strip().split('=', 1)
                parameters[unquote_plus(key.strip())] = unquote_plus(value.strip())

        # Check for required fields in headers
        if not check_fields(headers, method):
            raise BadRequestError

        file_name = uri.split('/')[-1]

        # app-add
        if file_name == "app-add":
            if method != "POST":
                raise MethodError
            first = False
            last = False
            for key, value in parameters.items():
                if key == "first": first = value
                elif key == "last": last = value
            if not first or not last:
                raise BadRequestError
            save_to_db(first, last)
            with open("www-data/app_add.html", "rb") as handle:
                body = handle.read()
            (tip, encoding) = mimetypes.guess_type("www-data/app_add.html", strict=True)
        # app-index
        elif file_name == "app-index":
            if method != "GET":
                raise MethodError
            filtered = read_from_db(parameters)
            table = ""
            for row in filtered:
                table_row = TABLE_ROW % (row["number"], row["first"], row["last"])
                table += table_row
            with open("www-data/app_list.html", "r") as handle:
                body = handle.read()
            body = body.replace("{{students}}", table).encode()
            (tip, encoding) = mimetypes.guess_type("www-data/app_list.html", strict=True)
        # app-json
        elif file_name == "app-json":
            if method != "GET":
                raise MethodError
            body = json.dumps(read_from_db(parameters)).encode()
            tip = "application/json"
        # Static files
        else:
            # Check URI
            if uri[-1] == '/':
                if headers.get('Host'): final_uri = "http://" + headers.get('Host') + uri + "index.html"
                elif headers.get('host'): final_uri = "http://" + headers.get('host') + uri + "index.html"
                head = RESPONSE_301 % final_uri
                client.write(head.encode("utf-8"))
                with open("www-data/" + uri + "index.html", "rb") as handle:
                    body = handle.read()
            else:
                uri = "/www-data" + uri
                if isdir("." + uri):
                    with open(uri[1:] + "/index.html", "rb") as handle:
                        body = handle.read()
                else:
                    with open(uri[1:], "rb") as handle:
                        body = handle.read()
            (tip, encoding) = mimetypes.guess_type(uri, strict=True)

        # Create the response
        if tip is None: tip = "application/octet-stream"
        head = HEADER_RESPONSE_200 % (tip, len(body))

        # Write the response back to the socket
        client.write(head.encode("utf-8"))
        client.write(body)
    except ValueError:
        client.write(RESPONSE_400.encode("utf-8"))
    except IOError:
        client.write(RESPONSE_404.encode("utf-8"))
    except MethodError:
        client.write(RESPONSE_405.encode("utf-8"))
    except BadRequestError:
        client.write(RESPONSE_400.encode("utf-8"))
    finally:
        client.close()


def main(port):
    """Starts the server and waits for connections."""

    server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
    server.bind(("", port))
    server.listen(1)

    print("Listening on %d" % port)

    while True:
        connection, address = server.accept()
        print("[%s:%d] CONNECTED" % address)
        process_request(connection, address)
        connection.close()
        print("[%s:%d] DISCONNECTED" % address)


if __name__ == "__main__":
    main(8080)
