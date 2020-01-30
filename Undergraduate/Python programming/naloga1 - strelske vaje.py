from math import *
v = float(input("Hitrost? "))
kot = float(input ("Kot? "))
kot = kot * 3.14 / 180
s = ((v ** 2) * sin(2 * kot)) / 9.81
print ("Krogla leti " + str(s) + " metrov.")


