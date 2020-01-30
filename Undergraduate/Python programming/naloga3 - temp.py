ljubljana = [28, 30, 25, 28, 30, 32, 35, 28, 25, 27]
maribor = [30, 28, 26, 34, 26, 32, 34, 30, 28, 28]
celje = [28, 32, 30, 31, 32, 28, 27, 26, 25, 25]
koper = [32, 35, 35, 31, 32, 34, 35, 30, 28, 28]
kranj = [28, 27, 30, 32, 28, 27, 26, 28, 30, 25]

'''
max_temp_razlika = 0
max_mesto = 0
for lj, mb in zip(ljubljana, maribor):
    if abs(lj - mb) > max_temp_razlika:
        max_temp_razlika = abs(lj - mb)
        if lj - mb > 0:
            max_mesto = "Ljubljana"
        elif lj - mb < 0:
            max_mesto = "Maribor"
        else:
            max_mesto = "Oba isto"

print(max_temp_razlika, max_mesto)
'''

count = 0
for lj, mb, ce, kp, kr in zip(ljubljana, maribor, celje, koper, kranj):
    povprecje = 0
    povprecje = (lj + mb + ce + kp + kr) / 5
    if povprecje > 30:
        count += 1
print("30 stopinj ali več je v", count, "krajih..")



