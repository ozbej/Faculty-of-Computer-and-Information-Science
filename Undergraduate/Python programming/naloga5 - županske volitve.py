def glasov(obkrozeno):
    count = 0
    for i in obkrozeno:
        if i:
            count += 1
    return count

def voljeni(obkrozeno, imena):
    glasovnica = {}
    for obkrozen, ime in zip(obkrozeno, imena):
        if glasov(obkrozeno) != 1:
            return None
        glasovnica[ime] = obkrozen
        if obkrozen:
            return ime

def najvecji(d):
    max_k = ""
    max_v = None
    if d == {}:
        return (None, None)
    for k in d:
        if max_v == None or d[k] > max_v:
            max_v = d[k]
            max_k = k
    return max_k, d[max_k]

def pristej(s, t):
    for i in t:
        if i in s:
            s[i] += t[i]
        else:
            s[i] = t[i]

s = {"a": 5, "b": 6, "d": 4}
t = {"b": 3, "c": 8}
pristej(s, t)
print(s)

def zmagovalec(glasovnice):
    rez = {}
    max_key = ""
    max_value = 0
    i = 0
    for obkrozeno, imena in glasovnice:
        voljen = voljeni(obkrozeno, imena)
        if voljen != None:
            i += 1
        if (voljen not in rez):
            rez[voljen] = 0
        rez[voljen] += 1
        if rez[voljen] > max_value and voljen != None:
            max_value = rez[voljen]
            max_key = voljen

    return (max_key, rez[max_key] / i)

def voljeni_vec(obkrozeno, imena):
    glasovnica = {}
    rez = {}
    count = 0
    for obkrozen, ime in zip(obkrozeno, imena):
        glasovnica[ime] = obkrozen
        if obkrozen:
            count += 1
    for ime, obkrozen in glasovnica.items():
        if glasovnica[ime]:
            rez[ime] = 1 / count
    return rez


'''Dodatna:'''

def ujemanje(obljube, zelje):
    vsota = obljube + zelje
    rez = {}
    vse_zelje = 0
    ujemanja = 0
    for i in vsota:
        if i not in rez:
            rez[i] = 0
            if i in rez:
                vse_zelje += 1
        rez[i] += 1
    st = 0
    for j in rez:
        if rez[j] > 1:
            ujemanja += 1
    if vse_zelje != 0:
        rezultat = ujemanja / vse_zelje
    else:
        rezultat = 0
    return rezultat

def max_kljuc(s):
    max_key = None
    max_value = None
    for key in s:
        if max_value == None or s[key] > max_value:
            max_value = s[key]
            max_key = key
    return max_key

def naj_kandidata(kandidati, zelje):
    rez = {}
    for ime, obljube in kandidati:
        rez[ime] = ujemanje(obljube, zelje)
    najvecji = max_kljuc(rez)
    del rez[max_kljuc(rez)]
    drugi_najvecji = max_kljuc(rez)

    return najvecji, drugi_najvecji


import unittest

class TestObvezna(unittest.TestCase):
    def test_glasov(self):
        self.assertEqual(2, glasov([True, False, False, True]))
        self.assertEqual(1, glasov([True] + [False] * 1000))
        self.assertEqual(20, glasov([True] * 20 + [False] * 1000))
        self.assertEqual(0, glasov([False] * 1000))
        self.assertEqual(0, glasov([]))

    def test_voljeni(self):
        self.assertEqual("Anna", voljeni([True, False, False], ["Anna", "Berta", "Cilka"]))
        self.assertEqual("Berta", voljeni([False, True, False], ["Anna", "Berta", "Cilka"]))
        self.assertEqual("Cilka", voljeni([False, False, True], ["Anna", "Berta", "Cilka"]))
        self.assertEqual("Cilka", voljeni([True], ["Cilka"]))

        self.assertIsNone(voljeni([True, False, True], ["Anna", "Berta", "Cilka"]))
        self.assertIsNone(voljeni([False, False, False], ["Anna", "Berta", "Cilka"]))
        self.assertIsNone(voljeni([False], ["Cilka"]))
        self.assertIsNone(voljeni([True, True, True], ["Anna", "Berta", "Cilka"]))
        self.assertIsNone(voljeni([], []))

    def test_najvecji(self):
        a, b, c, d = "abcd"
        self.assertEqual((d, 42), najvecji({a: 15, b: 30, c: 8, d: 42}))
        self.assertEqual((c, -5), najvecji({a: -10, b: -30, c: -5, d: -42}))
        self.assertEqual((c, "Dani"), najvecji({a: "Anna", b: "Berta", c: "Dani", d: "Cilka"}))
        self.assertEqual((None, None), najvecji({}))


    def test_pristej(self):
        a, b, c, d = "abcd"
        s = {a: 5, b: 6, d: 4}
        t = {b: 3, c: 8}
        self.assertIsNone(pristej(s, t))
        self.assertDictEqual({a: 5, b: 9, c: 8, d: 4}, s)
        self.assertDictEqual({b: 3, c: 8}, t)

        e = {}
        self.assertIsNone(pristej(s, e))
        self.assertDictEqual({a: 5, b: 9, c: 8, d: 4}, s)
        self.assertDictEqual({}, e)

        self.assertIsNone(pristej(e, t))
        self.assertDictEqual({b: 3, c: 8}, e)

    def test_zmagovalec(self):
        zmago, delez = zmagovalec(
            [([False, True, True], ["Berrta", "Ana", "Cilka"]),  # neveljavna
             ([False, True, True], ["Berrta", "Ana", "Cilka"]),  # neveljavna
             ([False, False, True], ["Ana", "Cilka", "Berrta"]), # Berrta
             ([False, True, False], ["Ana", "Berrta", "Cilka"]), # Berrta
             ([False, True, False], ["Berrta", "Ana", "Cilka"])  # Ana
             ])
        self.assertEqual(zmago, "Berrta")
        self.assertAlmostEqual(2 / 3, delez)

        zmago, delez = zmagovalec(
            [([False, True, True], ["Berrta", "Ana", "Cilka"]),  # neveljavna
             ([False, True, True], ["Berrta", "Ana", "Cilka"]),  # neveljavna
             ([False, True, False], ["Ana", "Cilka", "Berrta"]), # Berrta
             ])
        self.assertEqual(zmago, "Cilka")
        self.assertAlmostEqual(1, delez)


    def test_voljeni_vec(self):
        self.assertEqual(
            {"Ana": .5, "Cilka": .5},
            voljeni_vec([False, True, True], ["Berrta", "Ana", "Cilka"]))
        self.assertEqual(
            {"Cilka": 1},
            voljeni_vec([False, False, True], ["Berrta", "Anna", "Cilka"]))
        self.assertEqual(
            {"Cilka": 0.25, "Berrta": 0.25, "Anna": 0.25, "Dani": 0.25},
            voljeni_vec([True] * 4, ["Berrta", "Anna", "Cilka", "Dani"]))
        self.assertEqual({}, voljeni_vec([False, False], ["Ana", "Berta"]))
        self.assertEqual({}, voljeni_vec([], []))


class TestDodatna(unittest.TestCase):
    def test_ujemanje(self):
        self.assertAlmostEqual(1 / 6, ujemanje(list("abc"), list("cdef")))
        self.assertAlmostEqual(0.5, ujemanje(list("abc"), list("acd")))
        self.assertAlmostEqual(0, ujemanje(list("abc"), list("def")))
        self.assertAlmostEqual(0, ujemanje([], list("acd")))
        self.assertAlmostEqual(0, ujemanje(list("acd"), []))
        self.assertAlmostEqual(0, ujemanje([], []))
        self.assertAlmostEqual(1, ujemanje(list("abc"), list("bca")))
        self.assertAlmostEqual(0.5, ujemanje(list("abcdef"), list("abc")))
        self.assertAlmostEqual(0.5, ujemanje(list("defabc"), list("abc")))
        self.assertAlmostEqual(0.5, ujemanje(list("d"), list("ad")))
        self.assertAlmostEqual(0.5, ujemanje(list("d"), list("ad")))
        self.assertAlmostEqual(1, ujemanje(list("d"), list("d")))

    def test_naj_kandidati(self):
        kandidati = [("Ana", list("abc")),
                     ("Berta", list("bcd")),
                     ("Cilka", list("abcdefg")),
                     ("Dani", list("d")),
                     ("Ema", list("adefgh"))]
        self.assertEqual(("Ana", "Berta"), naj_kandidata(kandidati, list("abc")))
        self.assertEqual(("Dani", "Berta"), naj_kandidata(kandidati, list("d")))


if __name__ == "__main__":
    unittest.main()
