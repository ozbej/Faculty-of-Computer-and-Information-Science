from random import *

def najvecja_potenca(n):
    i = 0
    while((2 ** i) <= n):
        i += 1
    x = 2 ** i
    if(x >= n):
        return 2 ** (i - 1)

def razbij(n):
    s = []
    while n > 0:
        s.append(najvecja_potenca(n))
        n = n - najvecja_potenca(n)
    return s

def vsebuje(s, x):
    for i in s:
        if i == x:
            return True
            break
    else:
        return False

def razlika(s, t):
    u = []
    for i in t:
        if vsebuje(s, i) == False:
            u.append(i)
    for j in s:
        if vsebuje(t, j) == False:
            u.append(j)
    return u

def razbij_seznam(s):
    razbit_s = []
    for i in s:
        razbit_s.append(razbij(i))
    return razbit_s

def vsota(s):
    vsota = 0
    for i in s:
        vsota += i
    return vsota

def zdruzi_sezname(s):
    zdruzen_s = []
    for i in s:
        zdruzen_s = razlika(zdruzen_s, i)
    return zdruzen_s

'''Osnovna:
def poteza_racunalnik(s):
    a = randint(0, (len(s) - 1))
    for st, i in enumerate(s):
        if st == a:
            b = randint(1, i)
    return a, b
    '''

def helper(t, s):
    if len(t) == 1:
        return t
    else:
        u = t
        for num in u:
            for num2 in u:
                if num + num2 <= max(s) and num + num2 not in t:
                    t.append(num + num2)
        return t

def poteza_racunalnik(s):
    t = zdruzi_sezname(razbij_seznam(s))
    if t != []:
        t = helper(t, s)
        for i in t:
            for j, (num) in enumerate(s):
                if num - i >= 0:
                    s[j] -= i
                    if zdruzi_sezname(razbij_seznam(s)) == []:
                        s[j] += i
                        return j, i
                    else:
                        s[j] += i
    while 1:
        vrstica = randint(0, len(s) - 1)
        if s[vrstica] is not 0:
            return vrstica, randint(1, s[vrstica])

### ^^^ Moja koda zgoraj ^^^ ###

import unittest

class TestObvezna(unittest.TestCase):
    def test_najvecja_potenca(self):
        self.assertEqual(najvecja_potenca(20), 16)
        self.assertEqual(najvecja_potenca(16), 16)
        self.assertEqual(najvecja_potenca(5), 4)
        self.assertEqual(najvecja_potenca(2 ** 10000), 2 ** 10000)
        self.assertEqual(najvecja_potenca(1), 1)

    def test_razbij(self):
        self.assertEqual(razbij(22), [16, 4, 2])
        self.assertEqual(razbij(23), [16, 4, 2, 1])
        self.assertEqual(razbij(56), [32, 16, 8])
        self.assertEqual(razbij(2 ** 10000 + 2 ** 5000 + 5), [2 ** 10000, 2 ** 5000, 4, 1])
        self.assertEqual(razbij(1), [1])
        self.assertEqual(razbij(0), [])

    def test_vsebuje(self):
        self.assertTrue(vsebuje([4, 1, 5, 2, 8], 4))
        self.assertTrue(vsebuje([4, 1, 5, 2, 8], 1))
        self.assertTrue(vsebuje([4, 1, 5, 2, 8], 5))
        self.assertTrue(vsebuje([4, 1, 5, 2, 8], 2))
        self.assertTrue(vsebuje([4, 1, 5, 2, 8], 8))
        self.assertTrue(vsebuje([4], 4))
        self.assertTrue(vsebuje([4, 0, 1], 0))
        self.assertTrue(vsebuje([0], 0))

        self.assertFalse(vsebuje([4, 1, 5, 2, 8], 42))
        self.assertFalse(vsebuje([], 42))
        self.assertFalse(vsebuje([], 0))

    def test_razlika(self):
        self.assertEqual(sorted(razlika([1, 2, 4], [1, 4, 8])), [2, 8])
        self.assertEqual(sorted(razlika([4, 1, 16], [1, 8, 2])), [2, 4, 8, 16])
        self.assertEqual(sorted(razlika([1, 2, 5, 13, 7], [13, 7, 1, 2])), [5])
        self.assertEqual(sorted(razlika([1, 2, 5, 13, 7], [13, 7, 1, 2, 5])), [])
        self.assertEqual(sorted(razlika([1, 2, 5, 13, 7], [])), [1, 2, 5, 7, 13])
        self.assertEqual(sorted(razlika([], [42])), [42])
        self.assertEqual(sorted(razlika([42], [42])), [])
        self.assertEqual(sorted(razlika([], [])), [])

    def test_razbij_seznam(self):
        self.assertEqual(razbij_seznam([22, 56, 7]), [[16, 4, 2], [32, 16, 8], [4, 2, 1]])
        self.assertEqual(razbij_seznam([22]), [[16, 4, 2]])
        self.assertEqual(razbij_seznam([]), [])

    def test_zdruzi_sezname(self):
        self.assertEqual(sorted(zdruzi_sezname([[16, 4, 2], [32, 16, 8], [4, 2, 1]])), [1, 8, 32])
        self.assertEqual(zdruzi_sezname([[1, 2, 3]]), [1, 2, 3])
        self.assertEqual(zdruzi_sezname([]), [])

    def test_vsota(self):
        self.assertEqual(vsota([5, 1, 2, 5]), 13)
        self.assertEqual(vsota([5, 1, 2, 5] + [42] * 100), 4213)
        self.assertEqual(vsota([]), 0)

    def test_poteza_racunalnik(self):
        for s in ([2, 8, 9, 3], [1, 2, 4, 8, 16, 32, 64, 128, 255]):
            poteze = set()
            for i in range(5000):
                vrstica, kovancev = poteza_racunalnik(s)
                self.assertLess(vrstica, len(s), "preveilka številka vrstice")
                self.assertLessEqual(kovancev, s[vrstica], "v tej vrstici ni dovolj kovancev")
                poteze.add((vrstica, kovancev))
            self.assertGreater(len(poteze), 10, "poteze niso videti naključne")
            self.assertEqual(len({v for v, _ in poteze}), len(s), "poteze niso naključne - nekatere vrstice niso nikoli izbrane")


class TestDodatna(unittest.TestCase):
    def test_poteza_racunalnik(self):
        from random import randint
        from functools import reduce
        from operator import xor

        for i in range(1000):
            s = [randint(3, 10) for _ in range(randint(3, 10))]
            v, k = poteza_racunalnik(s)
            t = s[:]
            t[v] -= k
            self.assertEqual(
                reduce(xor, s) * reduce(xor, t), 0,
                f"pri {s} je predlagal {(v, k)}, kar ni optimalno")


if __name__ == "__main__":
    unittest.main()

