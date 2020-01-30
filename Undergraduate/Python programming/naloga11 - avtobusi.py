import unittest
from unittest.mock import Mock


class Potnik:
    def __init__(self, vstop, izstop):
        self.vstop = vstop
        self.izstop = izstop

    def __repr__(self):
        return f"Potnik({self.vstop}, {self.izstop})"

    __str__ = __repr__


class Avtobus:
    def __init__(self, max_potnikov):
        self.max_potnikov = max_potnikov
        self.st_potnikov = 0
        self.folk_gor = []

    def kapaciteta(self):
        return self.max_potnikov

    def stevilka_avtobusa(self):
        return 18

    def zasedenost(self):
        return self.st_potnikov

    def vstop(self, potniki):
        for potnik in potniki:
            if self.st_potnikov < self.max_potnikov:
                self.st_potnikov += 1
                self.folk_gor.append(potnik)

        return [potnik for potnik in potniki if potnik not in self.folk_gor]

    def izstop(self, stevilka_postaje):
        folk_se_gor = []

        for potnik in self.folk_gor:
            if potnik.izstop == stevilka_postaje:
                self.st_potnikov -= 1
            else:
                folk_se_gor.append(potnik)

        self.folk_gor = folk_se_gor

    def postaja(self, stevilka_postaje, potniki):
        self.izstop(stevilka_postaje)
        return self.vstop(potniki)


#def simulacija(potniki, avtobusi, postaje):


class TestObvezna(unittest.TestCase):
    def test_podatki(self):
        a = Avtobus(42)
        self.assertEqual(a.stevilka_avtobusa(), 18)
        self.assertEqual(a.zasedenost(), 0)
        self.assertEqual(a.kapaciteta(), 42)

        b = Avtobus(13)
        self.assertEqual(a.kapaciteta(), 42)
        self.assertEqual(b.kapaciteta(), 13)

    def test_vstop(self):
        a = Avtobus(5)
        b = Avtobus(3)

        self.assertEqual(a.vstop([Potnik(1, 2) for _ in range(3)]), [])
        self.assertEqual(b.vstop([Potnik(1, 2) for _ in range(3)]), [])
        self.assertEqual(a.zasedenost(), 3)
        self.assertEqual(a.kapaciteta(), 5)
        self.assertEqual(b.zasedenost(), 3)
        self.assertEqual(b.kapaciteta(), 3)

        cakajoci = [Potnik(2, 3) for i in range(5)]
        self.assertEqual(a.vstop(cakajoci), cakajoci[2:])
        self.assertEqual(b.vstop(cakajoci), cakajoci)
        self.assertEqual(a.zasedenost(), 5)
        self.assertEqual(a.kapaciteta(), 5)
        self.assertEqual(b.zasedenost(), 3)
        self.assertEqual(b.kapaciteta(), 3)

        cakajoci = [Potnik(3, 4) for i in range(5)]
        self.assertEqual(a.vstop(cakajoci), cakajoci)
        self.assertEqual(a.zasedenost(), 5)
        self.assertEqual(a.kapaciteta(), 5)
        self.assertEqual(b.zasedenost(), 3)
        self.assertEqual(b.kapaciteta(), 3)

    def test_izstop(self):
        a = Avtobus(20)

        potniki = [Potnik(1, izstop)
                   for izstop in [2, 2, 3, 2, 4, 3, 3, 2, 3, 3, 3, 4, 2]]
        a.vstop(potniki)
        self.assertEqual(a.zasedenost(), 13)

        a.izstop(2)
        self.assertEqual(a.zasedenost(), 8)

        a.izstop(3)
        self.assertEqual(a.zasedenost(), 2)

        a.izstop(5)
        self.assertEqual(a.zasedenost(), 2)

        a.izstop(4)
        self.assertEqual(a.zasedenost(), 0)

    def test_postaja(self):
        a = Avtobus(3)
        a.vstop = Mock()
        a.izstop = Mock()
        potniki = [Potnik(1, 8)]
        a.postaja(1, potniki)
        a.vstop.assert_called_once_with(potniki)
        a.izstop.assert_called_once_with(1)

        a = Avtobus(3)
        potniki = [Potnik(1, 8)]
        a.postaja(1, potniki)
        potniki = [Potnik(8, i) for i in range(10, 15)]
        self.assertEqual(a.postaja(8, potniki), potniki[3:])


class TestDodatna(unittest.TestCase):
    def test_simulacija(self):
        avtobusi = [Avtobus(3), Avtobus(4), Avtobus(2)]
        potniki = """
##### (1)
## (1)
 ##### (1)
 ## (2)
 ####### (2)
  ######## (1)
  ######## (2)
  ######### (2)
   ######### (2)
   ####### (3)
   #### (3)
   # (X)
   #### (X)
    ## (X)
      ##### (1)     
       ## (3)"""
        potniki = [Potnik(p.find("#"), p.rfind("#") + 1) for p in potniki.strip().splitlines()]
        self.assertEqual(
            simulacija(potniki, avtobusi, list(range(20))),
            potniki[-5:-2])


if __name__ == "__main__":
    unittest.main()

