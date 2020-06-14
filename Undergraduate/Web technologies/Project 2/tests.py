"""Tests for the HTTP server

To run the tests simply issue:
  python tests.py

To create (or delete) an example database of users, use the following command:
 $ python tests.py create_db
or
 $ python tests.py delete_db
"""
import sys
import time
import unittest
from multiprocessing import Process
from os import remove
from random import randint
from urllib.request import urlopen

from server import *

MISSING_REQUESTS = """[WARNING] Your system is missing the Requests package.

Requests is a Python library that greatly simplifies writing HTTP requests
and parsing HTTP responses. (Even NSA is supposedly using it; you should be
too.) Visit Requests homepage for installation instructions:
  http://docs.python-requests.org

In most systems, the installation only requires that you open your command
prompt and write the following command:
  pip install requests

Note that you can still issue HTTP requests and parse HTTP responses without
the Requests library, but you'll have to write a lot more code. Below, you'll
find an example of a test that does not use the Request library; it uses the
built-in package 'urllib' and the 'urlopen(str)' function. The name of the test
is 'test_get_root_index_urlopen'.

Also note that the test 'test_get_root_index_requests' which currently uses the
Requests package will fail, until you install the package (or remove the test).
"""

try:
    import requests
except ImportError:
    print('\033[91m' + '\033[1m' + MISSING_REQUESTS + '\033[0m')

DATA = [{"number": 1, "first": "alice", "last": "cooper"},
        {"number": 2, "first": "bob", "last": "marley"},
        {"number": 3, "first": "bob", "last": "dylan"},
        {"number": 4, "first": "charlie", "last": "pooth"},
        {"number": 5, "first": "david", "last": "bowie"}]


class ServerTest(unittest.TestCase):
    """Unit tests for the Python HTTP server.

    You are highly encouraged to write additional tests."""

    def setUp(self):
        """Runs before very test. Do not modify."""
        self.host = "127.0.0.1"
        self.port = randint(30000, 50000)
        self.server = "http://%s:%d" % (self.host, self.port)
        self.process = Process(target=main, args=(self.port,))
        self.process.daemon = True
        self.process.start()
        self.remove_file(PICKLE_DB)
        time.sleep(0.01)

    def remove_file(self, filename):
        """Remove the DB (pickle) file. Do not to modify."""
        try:
            remove(filename)
        except OSError:
            pass

    def tearDown(self):
        """Runs after very test. Do not to modify."""
        self.process.terminate()
        self.remove_file(PICKLE_DB)

    def prepare_db_data(self):
        """Prepares some DB data and saves it to the DB"""
        for item in DATA:
            save_to_db(item["first"], item["last"])

    # A helper method to send raw data over a TCP socket
    # You can find a usage example below
    def _manual_request(self, payload):
        """Sends a raw request over a TCP socket."""
        client = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        time.sleep(0.25)
        client.connect((self.host, self.port))
        client.sendall(payload.encode("UTF-8"))
        response = client.recv(8192).decode("UTF-8")
        client.close()
        return response

    ##################################################################
    #                        UNIT TESTS
    #
    # These tests check whether small chunks of your code (functions)
    # work correctly. They should help you build and ensure the
    # well-functioning of smaller units of your code. For instance,
    # a unit test can check whether your parse_header function (if you
    # decide to write one) works correctly.
    #
    # A few examples are provided below, but you should write many
    # more yourself.
    ##################################################################

    def test_db_writes_and_reads(self):
        """Data should persist in the DB"""
        self.prepare_db_data()

        for original, read in zip(DATA, read_from_db()):
            self.assertEqual(original, read)

    def test_db_filter_single(self):
        """DB should be filterable by either number, first or last name"""
        self.prepare_db_data()

        entry = read_from_db({"number": 1})
        self.assertEqual(len(entry), 1)
        self.assertEqual(entry[0]["number"], 1)

        entries = read_from_db({"first": "bob"})
        self.assertEqual(len(entries), 2)

        for entry in entries:
            self.assertEqual(entry["first"], "bob")
            self.assertTrue(entry["last"] in ("dylan", "marley"))

        entry = read_from_db({"last": "cooper"})
        self.assertEqual(len(entry), 1)
        self.assertEqual(entry[0]["last"], "cooper")

    def test_db_filter_combined(self):
        """DB should be filterable by last and first name"""
        self.prepare_db_data()

        entry = read_from_db({"first": "alice", "last": "cooper"})
        self.assertEqual(len(entry), 1)
        self.assertEqual(entry[0]["first"], "alice")
        self.assertEqual(entry[0]["last"], "cooper")

    # Add your unit tests below.
    # Below is an example of a function that parses the request line
    def test_parse_request_line1(self):
        """Parse request line 'GET / HTTP/1.1'"""

        # method, uri, protocol, params = parse_request_line("GET / HTTP/1.1")
        # self.assertEqual(method, "GET")
        # self.assertEqual(uri, "/")
        # self.assertEqual(protocol, "HTTP/1.1")
        # self.assertEqual(params, {})

    ###################################################################
    #                      INTEGRATION TESTS
    # These tests verify the whether your server works correctly.
    # Each test starts the server, sends an HTTP request and then
    # checks whether the HTTP response is valid.
    #
    # These kinds of tests will be used for grading. As with unit
    # tests, you should write a lot of additional tests yourself.
    ###################################################################

    # This method sends a request using urllib (built-in) library
    def test_get_root_index_urlopen(self):
        """Return code 200 when a GET request is made to /index.html (urlopen)"""
        response = urlopen(self.server + "/index.html")
        headers = dict(response.headers.items())
        self.assertEqual(response.getcode(), 200)
        self.assertEqual(headers["content-type"], "text/html")
        html = response.read().decode("utf-8")
        self.assertNotEqual(html.lower().find("to-do"), -1)

    # This method sends a request using the Requests (3rd-party) library
    # This is the recommended approach to writing integration tests
    def test_get_root_index_requests(self):
        """Return code 200 when a GET request is made to /index.html (requests)"""
        response = requests.get(self.server)
        self.assertEqual(response.status_code, 200)
        self.assertEqual(response.headers["content-type"], "text/html")
        self.assertNotEqual(response.text.lower().find("to-do"), -1)

    # Occasionally, you'll want to send arbirary data to your server, for
    # instace, to test, how your server responds to invalid HTTP requests
    def test_invalid_request_line(self):
        """Return code 400 when the request line is invalid"""
        response = self._manual_request("This is really not an HTTP request\n")
        self.assertTrue(response.startswith("HTTP/1.1 400"))


if __name__ == '__main__':
    if len(sys.argv) == 2:
        test_db = ServerTest()
        if sys.argv[1] == "create_db":
            test_db.prepare_db_data()
        elif sys.argv[1] == "delete_db":
            test_db.remove_file(PICKLE_DB)
    else:
        unittest.main()
