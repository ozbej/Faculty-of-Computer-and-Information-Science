setwd("D:\\Users\\ozbej\\Documents\\FRI\\2. letnik\\1. semester\\UI\\Seminarska 1")

#Knjiznice
#-------------------------------------------------------------------------------------------------------------------------------------
library(lubridate)
library(rpart)
library(e1071)
library(randomForest)
library(kknn)
library(CORElearn)
library(ipred)
library(adabag)
#-------------------------------------------------------------------------------------------------------------------------------------

podatki <- read.table(file="podatkiSem1.txt", sep=",", header=TRUE)

#Dodajanje atributov
#-------------------------------------------------------------------------------------------------------------------------------------
#Dodajanje letnih casov
letni <- c()

for(i in podatki$Datum){
  datum = as.POSIXct(i)
  m = format(datum, "%m")
  m = as.integer(m)
  d = format(datum, "%d")
  d = as.integer(d)
  l = format(datum, "%y")
  if((m == 12 && d >= 21) || m == 1 || m == 2 || (m == 3 && d < 21)){
    letni <- c(letni, "Zima")
  }
  else if((m == 3 && d >= 21) || m == 4 || m == 5 || (m == 6 && d < 21)){
    letni <- c(letni, "Pomlad")
  }
  else if((m == 6 && d >= 21) || m == 7 || m == 8 || (m == 9 && d < 23)){
    letni <- c(letni, "Poletje")
  }
  else{
    letni <- c(letni, "Jesen")
  }
} 
podatki$LetniCas=letni
podatki$LetniCas=as.factor(podatki$LetniCas)

#Datum na NULL
podatki$Datum <- NULL
podatki_primerjanje <- podatki
podatki_reg <- podatki
podatki_datumi <- 
#-------------------------------------------------------------------------------------------------------------------------------------

#Vizualizacija
#-------------------------------------------------------------------------------------------------------------------------------------
plot(x=podatki$Vlaga_mean, y=podatki$Padavine_mean, xlab="Vlaga", ylab="Padavine", main="Padavine v odvisnosti od vlage", col="blue")

barplot(c(mean(podatki$Padavine_mean[podatki$LetniCas == "Pomlad"]), 
          mean(podatki$Padavine_mean[podatki$LetniCas == "Poletje"]), 
          mean(podatki$Padavine_mean[podatki$LetniCas == "Jesen"]),
          mean(podatki$Padavine_mean[podatki$LetniCas == "Zima"])), 
        names=c("Pomlad", "Poletje", "Jesen", "Zima"), main="Povprecne padavine glede na letni cas", col="#29a1c2")

barplot(c(mean(podatki$Sunki_vetra_mean[podatki$Postaja == "Ljubljana"]), mean(podatki$Sunki_vetra_mean[podatki$Postaja == "Koper"])), 
        names=c("Ljubljana", "Koper"), main="Povprecna moc vetra glede na postajo", col="#87a6e0")

plot(x=podatki$O3, y=podatki$Temperatura_lokacija_mean, xlab="O3", ylab="Temperatura", main="Ozon v odvisnosti od temperature", col="#fa5050")

barplot(c(mean(podatki$O3[podatki$LetniCas == "Pomlad"]), 
          mean(podatki$O3[podatki$LetniCas == "Poletje"]), 
          mean(podatki$O3[podatki$LetniCas == "Jesen"]),
          mean(podatki$O3[podatki$LetniCas == "Zima"])), 
        names=c("Pomlad", "Poletje", "Jesen", "Zima"), main="Ozon glede na letni cas", col="#bf74c4")

plot(x=podatki$PM10, y=podatki$Temperatura_lokacija_mean, xlab="PM10", ylab="Temperatura", main="PM10 v odvisnosti od temperature", col="blue")
#-------------------------------------------------------------------------------------------------------------------------------------

#Klasifikacija
#-------------------------------------------------------------------------------------------------------------------------------------
pm10_cut <- cut(podatki$PM10, c(-1000, 35, 1000), labels=c("Nizka", "Visoka"))
podatki$PM10=pm10_cut
ozon_cut <- cut(podatki$O3, c(-1000, 60, 120, 180, 1000), labels=c("Nizka", "Srednja", "Visoka", "Ekstremna"))
podatki$O3=ozon_cut
PM10_mnozica <- podatki
PM10_mnozica$O3 <- NULL

# Funkcija za klasifikacijsko tocnost
CA <- function(prave, napovedane)
{
  t <- table(prave, napovedane)
  
  sum(diag(t)) / sum(t)
}

#1. model- koncentracija PM10 delcev ~ Odlocitveno drevo
set.seed(76543)
x <- sample(1:nrow(PM10_mnozica), size=as.integer(nrow(PM10_mnozica) * 0.7), replace=F)

ucna <- PM10_mnozica[x,]
testna <- PM10_mnozica[-x,]
#Tocnost vecinskega klasifikatorja
table(ucna$PM10)
sum(testna$PM10 == "Nizka") / length(testna$PM10)

drevo <- rpart(PM10 ~ ., data = ucna)
drevo
plot(drevo)
text(drevo, pretty = 0)

observed <- testna$PM10
head(observed)

predicted <- predict(drevo, testna, type="class")
head(predicted)

tab <- table(observed, predicted)
tab

CA(observed, predicted)

#2. model- ozon ~ Odlocitveno drevo
O3_mnozica <- podatki
O3_mnozica$PM10 <- NULL

x <- sample(1:nrow(O3_mnozica), size=as.integer(nrow(O3_mnozica) * 0.7), replace=F)

ucna <- O3_mnozica[x,]
testna <- O3_mnozica[-x,]

#Tocnost vecinskega klasifikatorja 
table(ucna$O3)
sum(testna$O3 == "Srednja") / length(testna$O3)

drevo2 <- rpart(O3 ~ ., data = ucna)
drevo2
plot(drevo2)
text(drevo2, pretty = 0)

observed <- testna$O3
head(observed)

predicted <- predict(drevo2, testna, type="class")
head(predicted)

tab <- table(observed, predicted)
tab

CA(observed, predicted)

#2.1. model - Podmoznica podatkov (ozon poleti) ~ Odlocitveno drevo
podmnozica1 <- podatki
podmnozica1$Glob_sevanje_min <- NULL
sort(attrEval(O3 ~ ., podmnozica1, "ReliefFequalK"), decreasing = TRUE)
#ce zbrisemo te atribute, se CA poboljsa
podmnozica1$Padavine_sum <- NULL
podmnozica1$Padavine_mean <- NULL
podmnozica1$Hitrost_vetra_min <- NULL
podmnozica1$Sunki_vetra_min <- NULL
podmnozica1$Postaja <- NULL
podmnozica1$Hitrost_vetra_max <- NULL
podmnozica1$PM10 <- NULL


x <- sample(1:nrow(podmnozica1), size=as.integer(nrow(podmnozica1) * 0.7), replace=F)

ucna <- podmnozica1[x,]
testna <- podmnozica1[-x,]

#Tocnost vecinskega klasifikatorja 
sum(testna$O3 == "Srednja") / length(testna$O3)

drevo_pod <- rpart(O3 ~ ., data = ucna)
plot(drevo_pod)
text(drevo_pod, pretty = 0)

observed_pod <- testna$O3

predicted_pod <- predict(drevo_pod, testna, type="class")

CA(observed_pod, predicted_pod)

#3. model - ozon ~ Naivni Bayes in Brier score
# Funkcija za izracun Brierjeve mere
observed <- testna$O3
nb <- naiveBayes(O3 ~ ., data = ucna)
predicted <- predict(nb, testna, type="class")
CA(observed, predicted)
#-------------------------------------------------------------------------------------------------------------------------------------

#Regresija
#-------------------------------------------------------------------------------------------------------------------------------------
#Funkcije mer za ocenjevanje ucenja v regresiji
mae <- function(observed, predicted)
{
  mean(abs(observed - predicted))
}

rmae <- function(observed, predicted, mean.val) 
{  
  sum(abs(observed - predicted)) / sum(abs(observed - mean.val))
}

podatki_reg$Postaja <- NULL
podatki_reg$Glob_sevanje_min <- NULL

PM10_mnozica <- podatki_reg
PM10_mnozica$O3 <- NULL
x <- sample(1:nrow(PM10_mnozica), size=as.integer(nrow(PM10_mnozica) * 0.7), replace=F)

ucna <- PM10_mnozica[x,]
testna <- PM10_mnozica[-x,]

#1. model ~ Regresijsko drevo na PM10
#Regresijsko drevo
observed <- testna$PM10
rd.model <- rpart(PM10 ~ ., ucna)
predicted <- predict(rd.model, testna)
rmae(observed, predicted, mean(ucna$PM10))
plot(rd.model)
text(rd.model)

#Napake
printcp(rd.model)

#Porezano drevo brez minimalne napake
rd.model2 <- prune(rd.model, cp = 0.03)
plot(rd.model2)
text(rd.model2, pretty = 0)
predicted <- predict(rd.model2, testna)
rmae(observed, predicted, mean(ucna$PM10))

#2. model ~ Linearna regresija na O3
O3_mnozica <- podatki_reg
O3_mnozica$PM10 <- NULL
x <- sample(1:nrow(O3_mnozica), size=as.integer(nrow(O3_mnozica) * 0.7), replace=F)

ucna <- O3_mnozica[x,]
testna <- O3_mnozica[-x,]
#Linearna regresija
plot(podatki_reg$O3 ~ podatki_reg$Temperatura_lokacija_mean, xlab="Povprecna temperatura", ylab="O3", col="#fa5050")

model <- lm(O3 ~ Temperatura_lokacija_mean, podatki_reg)
abline(model)

# lokalno utezena linearna regresija
model2 <- loess(O3 ~ Temperatura_lokacija_mean, data = podatki_reg, span = 0.3, degree = 1)
ord <- order(podatki_reg$Temperatura_lokacija_mean)
lines(podatki_reg$Temperatura_lokacija_mean[ord], model2$fit[ord], col = "blue")

#3. model ~ Regresijsko drevo na O3
#Regresijsko drevo
rd.model <- rpart(O3 ~ ., ucna)
predicted <- predict(rd.model, testna)
observed <- testna$O3
rmae(observed, predicted, mean(ucna$O3))
plot(rd.model)
text(rd.model)

#4. model ~ KNN na O3
knn.model <- kknn(O3 ~ ., ucna, testna, k = 5)
predicted <- fitted(knn.model)
rmae(observed, predicted, mean(ucna$O3))
#KNN je manj ucinkovit, ker je prevec "nepomembnih" atributov

predict(model2, podatki_reg)
#-------------------------------------------------------------------------------------------------------------------------------------

#Kombiniranje modelov strojnega ucenja
#-------------------------------------------------------------------------------------------------------------------------------------
ozon_cut <- cut(podatki_primerjanje$O3, c(-1000, 60, 120, 180, 1000), labels=c("Nizka", "Srednja", "Visoka", "Ekstremna"))
podatki_primerjanje$O3=ozon_cut
podatki_primerjanje$Datum <- NULL
podatki_primerjanje$Glob_sevanje_min <- NULL
set.seed(1234969)

x <- sample(1:nrow(podatki_primerjanje), size=as.integer(nrow(podatki_primerjanje) * 0.7), replace=F)
ucna <- podatki_primerjanje[x,]
testna <- podatki_primerjanje[-x,]

#-------------------------------------------------------------------------------------------------------------------------------------
#Glasovanje na O3
dt <- CoreModel(O3 ~ ., ucna, model="tree")
nb <- CoreModel(O3 ~ ., ucna, model="bayes")
knn <- CoreModel(O3 ~ ., ucna, model="knn", kInNN = 5)

pred_dt <- predict(dt, testna, type = "class")
CA_dt <- CA(testna$O3, pred_dt)
CA_dt

pred_nb <- predict(nb, testna, type="class")
CA_nb <- CA(testna$O3, pred_nb)
CA_nb

pred_knn <- predict(knn, testna, type="class")
CA_knn <- CA(testna$O3, pred_knn)
CA_knn

zdruzeno <- data.frame(pred_dt, pred_nb, pred_knn)

head(zdruzeno)

# testni primer klasificiramo v razred z najvec glasovi
voting <- function(predictions)
{
  res <- vector()
  
  for (i in 1 : nrow(predictions))  	
  {
    vec <- unlist(predictions[i,])
    res[i] <- names(which.max(table(vec)))
  }
  
  res
}

predicted_class <- voting(zdruzeno)
predicted <- factor(predicted_class, levels=levels(podatki_primerjanje$O3))
CA(testna$O3, predicted)
  
#Random forest
rf <- randomForest(O3 ~ ., ucna)
predicted <- predict(rf, testna, type = "class")
CA(testna$O3, predicted)

#Boosting
bm <- boosting(O3 ~ ., ucna)
predictions <- predict(bm, testna)
names(predictions)

predicted <- predictions$class
CA(testna$O3, predicted)

#-------------------------------------------------------------------------------------------------------------------------------------
