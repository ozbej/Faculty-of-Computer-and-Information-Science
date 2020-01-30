''' Osnovna naloga
from random import *
from math import *
i = 0
st = 0
while i < 1000:
    x = 2 * random()-1
    y = 2 * random()-1
    if sqrt(x ** 2 + y ** 2) <= 1:
        st += 1
    i += 1

pi = (4 * st) / 1000
print("Pi je:", pi)
'''


'''Dodatna naloga:'''
from random import *
from math import *
i = 0
st = 0
tmp = 0
count = 1
while 1:
    while i < 1000 * count:
        x = 2 * random()-1
        y = 2 * random()-1
        if sqrt(x ** 2 + y ** 2) <= 1:
            st += 1
        i += 1

    pi = (4 * st) / (1000 * count)
    if ((tmp - pi) <= 0.000001) and ((tmp - pi) > 0):
        break
    tmp = pi
    count += 1

print("Pi je:", pi)
print("Prekinilo je po:", count, "krogih.")

