import risar
from math import *
from random import randint, choice
import time
from PyQt5.QtWidgets import QMessageBox

zoge = []
vx = []
vy = []
barve = [barva for barva in risar.barve if barva != risar.crna]

for i in range(30):
    #barva = risar.barva(randint(0, 255), randint(0, 255), randint(0, 255))
    barva = choice(barve)
    x, y = randint(10, risar.maxX-10), randint(10, risar.maxY-10)
    krog = risar.krog(x, y, 10, barva, 1)
    zoge.append(krog)
    vx_item = randint(-5, 5)
    vy_item = sqrt(5 ** 2 - vx_item ** 2)
    vx.append(vx_item)
    vy.append(vy_item)

cas = time.time()
miska = risar.krog(risar.miska[0], risar.miska[1], 30, risar.bela)
while 1:
    if time.time() - cas >= 20:
        exit("Poteklo je 20s")
    if not risar.klik:
        miska.setPos(risar.miska[0], risar.miska[1])
    for i in range(len(zoge)):
        zoga = zoge[i]
        zoga.setPos(zoga.x() + vx[i], zoga.y() + vy[i])
        if risar.klik:
            if zoga.x() <= miska.x() + 30 and zoga.x() >= miska.x() - 30 and zoga.y() <= miska.y() + 30 and zoga.y() >= miska.y() - 30:
                exit("Žoga je zadela miško")
        if not (10 < zoga.x() < risar.maxX - 10):
            vx[i] = -vx[i]
        if not (10 < zoga.y() < risar.maxY - 10):
            vy[i] = -vy[i]
    risar.cakaj(0.02)
risar.stoj()