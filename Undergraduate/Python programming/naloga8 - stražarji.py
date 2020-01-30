def razberi(vrstica):
    vrstica_split = []
    st = 0
    for i in vrstica.replace("-", " ").replace(":", " ").replace("[", "").replace("]", "").replace("#", "").split(" "):
        if i.isdigit() and st != 6:
            i = int(i)
        vrstica_split.append(i)
        if st == 6:
            if i.isdigit():
                vrstica_split[6] = int(i)
            else:
                vrstica_split[6] = None
        if st >= 6:
            break
        st += 1
    return tuple(vrstica_split)

def preberi_datoteko(ime_datoteke):
    time_log = []
    terka = []
    for vrstica in sorted(open(ime_datoteke)):
        vrstica = razberi(vrstica.strip())
        if vrstica[5] == "Guard":
            if terka != []:
                terka.clear()
            terka.append(vrstica[6])
            a = vrstica[6]
        elif len(terka) == 0:
            terka.append(a)
            terka.append(vrstica[4])
        else:
            terka.append(vrstica[4])
        if len(terka) == 3:
            time_log.append(tuple(terka))
            terka = []
    return time_log

def izpis_dogodka(strazar, spi, zbudi):
    return(f"{strazar:4}: {spi:02}-{zbudi:02}")

def naj_drugi(pari):
    max = 0
    max_drugi = 0
    for par in pari:
        if par[1] > max_drugi:
            max = par[0]
            max_drugi = par[1]
    return max

def najvecji(dict):
    max_k = 0
    max_v = 0
    if dict == {}:
        return (None, None)
    for k in dict:
        if dict[k] > max_v:
            max_v = dict[k]
            max_k = k
    return max_k

def naj_zaspanec(dogodki):
    sleep_log = {}
    for vrstica in dogodki:
        if vrstica[0] not in sleep_log:
            sleep_log[vrstica[0]] = vrstica[2] - vrstica[1]
        else:
            sleep_log[vrstica[0]] += vrstica[2] - vrstica[1]
    return najvecji(sleep_log)

def kdaj_spi(strazar, dogodki):
    dict_minut = {}
    for ime, zacetek, konec in dogodki:
        if ime == strazar:
            for minuta in range(zacetek, konec):
                if minuta not in dict_minut:
                    dict_minut[minuta] = 1
                else:
                    dict_minut[minuta] += 1
    return najvecji(dict_minut)

def naj_cas(dogodki):
    dict_minut = {}
    for ime, zacetek, konec in dogodki:
        for minuta in range(zacetek, konec):
            if (ime, minuta) not in dict_minut:
                dict_minut[(ime, minuta)] = 1
            else:
                dict_minut[(ime, minuta)] +=1
    return najvecji(dict_minut)

#---------------------------------------------------------------------
import unittest
import warnings

class Test01Obvezna(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        with open("primer.txt", "wt") as primer:
            primer.write("""[1518-11-01 00:00] Guard #10 begins shift
[1518-11-01 00:05] falls asleep
[1518-11-01 00:25] wakes up
[1518-11-01 00:30] falls asleep
[1518-11-01 00:55] wakes up
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-02 00:40] falls asleep
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:36] falls asleep
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-05 00:45] falls asleep
[1518-11-05 00:55] wakes up""")

        with open("primer2.txt", "wt") as primer:
            primer.write("""[1518-11-01 00:26] wakes up
[1518-11-01 00:05] falls asleep
[1518-11-05 00:45] falls asleep
[1518-11-04 00:36] falls asleep
[1518-11-01 23:58] Guard #99 begins shift
[1518-11-01 00:55] wakes up
[1518-11-02 00:40] falls asleep
[1518-11-01 00:00] Guard #10 begins shift
[1518-11-02 00:50] wakes up
[1518-11-03 00:05] Guard #10 begins shift
[1518-11-03 00:24] falls asleep
[1518-11-03 00:29] wakes up
[1518-11-04 00:02] Guard #99 begins shift
[1518-11-04 00:46] wakes up
[1518-11-05 00:03] Guard #99 begins shift
[1518-11-01 00:30] falls asleep
[1518-11-05 00:55] wakes up""")

    def test_razberi(self):
        self.assertEqual(
            razberi("[1518-11-09 23:58] Guard #853 begins shift"),
            (1518, 11, 9, 23, 58, "Guard", 853))
        self.assertEqual(
            razberi("[1518-04-02 00:30] falls asleep"),
            (1518, 4, 2, 0, 30, "falls", None))
        self.assertEqual(
            razberi("[1518-08-11 00:47] wakes up"),
            (1518, 8, 11, 0, 47, "wakes", None))
        self.assertEqual(
            razberi("[1518-07-05 23:57] Guard #2917 begins shift"),
            (1518, 7, 5, 23, 57, "Guard", 2917))
        self.assertEqual(
            razberi("[1518-09-06 23:57] Guard #73 begins shift"),
            (1518, 9, 6, 23, 57, "Guard", 73))

    def test_preberi(self):
        warnings.filterwarnings("ignore", category=ResourceWarning)

        self.assertEqual(
            preberi_datoteko("primer.txt"),
            [(10, 5, 25),
             (10, 30, 55),
             (99, 40, 50),
             (10, 24, 29),
             (99, 36, 46),
             (99, 45, 55)])
        self.assertEqual(
            preberi_datoteko("primer2.txt"),
            [(10, 5, 26),
             (10, 30, 55),
             (99, 40, 50),
             (10, 24, 29),
             (99, 36, 46),
             (99, 45, 55)])

    def test_izpis_dogodka(self):
        self.assertEqual(izpis_dogodka(1945, 13, 42), "1945: 13-42")
        self.assertEqual(izpis_dogodka(19, 13, 42), "  19: 13-42")
        self.assertEqual(izpis_dogodka(19, 5, 42), "  19: 05-42")
        self.assertEqual(izpis_dogodka(19, 5, 8), "  19: 05-08")

    def test_naj_drugi(self):
        self.assertEqual(naj_drugi([(5, 3), (8, 9), (13, 5), (10, 7)]), 8)

    def test_naj_zaspanec(self):
        warnings.filterwarnings("ignore", category=ResourceWarning)

        primer = preberi_datoteko("primer.txt")
        self.assertEqual(naj_zaspanec(primer), 10)
        primer2 = preberi_datoteko("input.txt")
        self.assertEqual(naj_zaspanec(primer2), 2663)


class Test02Dodatna(unittest.TestCase):
    def test_kdaj_spi(self):
        warnings.filterwarnings("ignore", category=ResourceWarning)

        primer = preberi_datoteko("input.txt")
        self.assertEqual(kdaj_spi(2663, primer), 38)

    def test_naj_cas(self):
        warnings.filterwarnings("ignore", category=ResourceWarning)

        primer = preberi_datoteko("input.txt")
        self.assertEqual(naj_cas(primer), (2917, 35))


if __name__ == "__test__":
    unittest.main()
