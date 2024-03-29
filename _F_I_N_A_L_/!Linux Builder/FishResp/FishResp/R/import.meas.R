import.meas <- function(file, n.chamber = c(1,2,3,4,5,6,7,8),
                        logger = c("AutoResp", "FishResp", "QboxAqua"),
                        start.measure = "00:00:00",
                        stop.measure = "23:59:59",
                        set.date.time = NA,
                        meas.to.wait = 0){

  V1 <- V2 <- V3 <- V4 <- V5 <- V6 <- V7 <- V8 <- V9 <- V10 <- NULL
  V11 <- V12 <- V13 <- V14 <- V15 <- V16 <- V17 <- V18 <- V19 <- NULL
  V20 <- V21 <- V22 <- V23 <- V24 <- V25 <- V26 <- V27 <- V28 <- NULL
  Phase.Type <- Phase <- Start.Meas <- NULL

  ### AutoResp format ###
  if (logger == "AutoResp"){
    MR.data.all<-read.table(file, sep = "\t", skip=38, header=F, strip.white=T)

    if (n.chamber == 1){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(MR.data.all$Ox.1, na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
    }
    else if (n.chamber == 2){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
    }
    else if (n.chamber == 3){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
    }
    else if (n.chamber == 4){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
    }
    else if (n.chamber == 5){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
    }
    else if (n.chamber == 6){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
    }
    else if (n.chamber == 7){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22, V24, V25))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6, MR.data.all$Ox.7), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
      if(all(is.na(MR.data.all$Ox.7))){MR.data.all$Ox.7[is.na(MR.data.all$Ox.7)] <- aaa}
    }
    else if (n.chamber == 8){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22, V24, V25, V27, V28))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7", "Temp.8", "Ox.8")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6, MR.data.all$Ox.7, MR.data.all$Ox.8), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
      if(all(is.na(MR.data.all$Ox.7))){MR.data.all$Ox.7[is.na(MR.data.all$Ox.7)] <- aaa}
      if(all(is.na(MR.data.all$Ox.8))){MR.data.all$Ox.8[is.na(MR.data.all$Ox.8)] <- aaa}
    }
    else{
      print("Please, choose the number of chambers between 1 and 8")
    }
  }

  ### FishResp format ###

  else if (logger == "FishResp"){
    MR.data.all<-read.table(file, sep = "\t", skip=1, header=F, strip.white=T)
    if (n.chamber == 1){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(MR.data.all$Ox.1, na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
    }
    else if (n.chamber == 2){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
    }
    else if (n.chamber == 3){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
    }
    else if (n.chamber == 4){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
    }
    else if (n.chamber == 5){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
    }
    else if (n.chamber == 6){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
    }
    else if (n.chamber == 7){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6, MR.data.all$Ox.7), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
      if(all(is.na(MR.data.all$Ox.7))){MR.data.all$Ox.7[is.na(MR.data.all$Ox.7)] <- aaa}
    }
    else if (n.chamber == 8){
      MR.data.all<-subset(MR.data.all, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7", "Temp.8", "Ox.8")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(c(MR.data.all$Ox.1, MR.data.all$Ox.2, MR.data.all$Ox.3, MR.data.all$Ox.4,
                   MR.data.all$Ox.5, MR.data.all$Ox.6, MR.data.all$Ox.7, MR.data.all$Ox.8), na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}
      if(all(is.na(MR.data.all$Ox.2))){MR.data.all$Ox.2[is.na(MR.data.all$Ox.2)] <- aaa}
      if(all(is.na(MR.data.all$Ox.3))){MR.data.all$Ox.3[is.na(MR.data.all$Ox.3)] <- aaa}
      if(all(is.na(MR.data.all$Ox.4))){MR.data.all$Ox.4[is.na(MR.data.all$Ox.4)] <- aaa}
      if(all(is.na(MR.data.all$Ox.5))){MR.data.all$Ox.5[is.na(MR.data.all$Ox.5)] <- aaa}
      if(all(is.na(MR.data.all$Ox.6))){MR.data.all$Ox.6[is.na(MR.data.all$Ox.6)] <- aaa}
      if(all(is.na(MR.data.all$Ox.7))){MR.data.all$Ox.7[is.na(MR.data.all$Ox.7)] <- aaa}
      if(all(is.na(MR.data.all$Ox.8))){MR.data.all$Ox.8[is.na(MR.data.all$Ox.8)] <- aaa}
    }
    else{
      print("Please, choose the number of chambers between 1 and 8")
    }
  }

  ### QboxAqua format ###

  else if (logger == "QboxAqua"){
    MR.data.all<-read.table(file, sep = ",", skip=2, header=F, strip.white=T)
    if (n.chamber == 1){
      MR.data.all<-subset(MR.data.all, select=c(V1, V9, V4, ncol(MR.data.all)))
      names(MR.data.all)<-c("Date.Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(MR.data.all)){MR.data.all[,i] = as.numeric(gsub(',','.', MR.data.all[,i]))}
      aaa <- max(MR.data.all$Ox.1, na.rm = TRUE)
      if(all(is.na(MR.data.all$Ox.1))){MR.data.all$Ox.1[is.na(MR.data.all$Ox.1)] <- aaa}

      ### Indexing measurement phases for QboxAqua
      MR.data.all$Phase[MR.data.all$Phase == "1"] <- "F"
      MR.data.all$Phase[MR.data.all$Phase == "0"] <- "M"
      bdrs <- which(c(FALSE, tail(MR.data.all$Phase,-1) != head(MR.data.all$Phase,-1)))
      bdrs <- paste(bdrs , MR.data.all$Phase[bdrs], sep = "")
      M.start <- grep('M', bdrs, value=TRUE)
      M.start <- as.integer(sub("M$", "", M.start))
      M.end <- grep('F', bdrs, value=TRUE)
      M.end <- as.integer(sub("F$", "", M.end)) - 1
      if(M.end[1] < M.start[1]){M.end <- M.end[-1]}

      for(i in 1:length(M.end)){
        MR.data.all$Phase[M.start[i]:M.end[i]] <- paste(MR.data.all$Phase[M.start[i]:M.end[i]], i, sep = "")
      }
    }
    else{
      print("If 'Qubit Systems' starts producing multi-chamber systems for aquatic respirometry, please contact us via email: fishresp@gmail.com")
    }
  }


  else{
    print("Please, choose the format of your data: AutoResp, FishResp or QboxAqua")
  }

  rm(aaa)

  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # Formatting Date & Time Entries
  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # AM PM problem solving
  PM.1 <- grepl("AM", MR.data.all[,1], fixed=TRUE)
  PM.2 <- grepl("am", MR.data.all[,1], fixed=TRUE)
  PM.3 <- grepl("PM", MR.data.all[,1], fixed=TRUE)
  PM.4 <- grepl("pm", MR.data.all[,1], fixed=TRUE)
  dts <- NULL

  if(PM.1 || PM.2 || PM.3 || PM.4 == TRUE){

    if(any(DATEFORMAT == "DMY")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%d/%m/%Y/ %I:%M:%S %p")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%d/%m/%Y/ %I:%M:%S %p")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%d/%m/%y"), format="d/m/y")
    }
    else if(any(DATEFORMAT == "MDY")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%m/%d/%Y/ %I:%M:%S %p")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%m/%d/%Y/ %I:%M:%S %p")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%m/%d/%y"), format="m/d/y")
    }
    else if(any(DATEFORMAT == "YMD")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%Y/%m/%d/ %I:%M:%S %p")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%Y/%m/%d/ %I:%M:%S %p")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%y/%m/%d"), format="y/m/d")
    }
    else{
      print("Please, choose the date format: DMY, MDY or YMD, where D-day, M-month, Y-year")
    }
  }
  else{
    if(any(DATEFORMAT == "DMY")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%d/%m/%Y/%H:%M:%S")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%d/%m/%Y/%H:%M:%S")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%d/%m/%y"), format="d/m/y")
    }
    else if(any(DATEFORMAT == "MDY")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%m/%d/%Y/%H:%M:%S")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%m/%d/%Y/%H:%M:%S")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%m/%d/%y"), format="m/d/y")
    }
    else if(any(DATEFORMAT == "YMD")){
      if(logger == "QboxAqua"){
        crdt<-strptime(as.character(set.date.time), "%Y/%m/%d/%H:%M:%S")
        crdt <- rep(crdt, length(MR.data.all$Date.Time))
        crdt$sec <- crdt$sec + MR.data.all$Date.Time
          if(any(crdt$sec == 60)){
            s<-which(crdt$sec == 60)
            crdt$sec[s] = 0
            crdt$min[s] = crdt$min[s]+1
          }else{
            }
        MR.data.all$Date.Time <- crdt
      }
      else{
        MR.data.all$Date.Time<-strptime(as.character(MR.data.all$Date.Time), "%Y/%m/%d/%H:%M:%S")
      }
      dts<-dates(strftime(MR.data.all$Date.Time, "%y/%m/%d"), format="y/m/d")
    }
    else{
      print("Please, choose the time format: DMY, MDY or YMD, where D-day, M-month, Y-year")
    }
  }

  x<-strftime(MR.data.all$Date.Time, "%H:%M:%S")
  MR.data.all$Real.Time<-chron::chron(times=x)
  rm(x)
  tms<-times(strftime(MR.data.all$Date.Time, "%H:%M:%S"))
    x<-chron::chron(dates = dts, times = tms,  format = c(dates = "yy-m-d", times = "h:m:s"))
    dts<-chron::chron(dates = dts,  format = "yy-m-d")
  MR.data.all$Date.Time<-x
  MR.data.all$Date<-dts
  MR.data.all$Real.Time<-tms
  rm(dts)
  rm(tms)
  rm(x)

  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # Removing Non-Measurement Data
  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # this is some simple code to get rid of all the flushing and acclimation phases
  MR.data.all$Phase.Type<-as.factor(substr(as.vector(MR.data.all$Phase),1,1))
  MR.data.all<-subset(MR.data.all, Phase.Type=="M")
  MR.data.all$Phase.Type<-NULL
  row.names(MR.data.all)<-NULL
  MR.data.all$Phase<-factor(MR.data.all$Phase)

  # here is a quick lesson in how to create an ordered factor
  # note the odering of levels for the factor 'Phase' in the current dataframe
  levels(MR.data.all$Phase)

  # now creating the re-ordered factor
  z <- MR.data.all$Phase
  z <- gsub("[M]","",z)
  z <- as.numeric(z)
  x <- ordered(MR.data.all$Phase, levels=paste(rep("M",length(levels(MR.data.all$Phase))), head(z, n=1):tail(z, n=1), sep=""))
  MR.data.all$Phase<-x
  rm(x)
  # note the new class of 'Phase' and the new ordering
  levels(MR.data.all$Phase)

  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # Removing the final measurement Phase (tail error)
  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  y <- length(which(MR.data.all$Phase==head(levels(MR.data.all$Phase),1), T))
  z <- length(which(MR.data.all$Phase==tail(levels(MR.data.all$Phase),1), T))
  if (y != z){
    MR.data.all<-subset(MR.data.all, Phase!=tail(levels(MR.data.all$Phase),1))
  }
  MR.data.all$Phase<-factor(MR.data.all$Phase)

  # Identification of time period (M1 error)
  for(i in 1:10){
    a <- paste("M", i, sep = "")
    ifelse(MR.data.all$Phase==a, x <- a, i<-i+1)
  }

  # cut off first n raws from 'M' phase
  if(meas.to.wait != 0){
    idx <- unlist(tapply(1:nrow(MR.data.all), MR.data.all$Phase, tail, -(meas.to.wait)), use.names=FALSE)
    MR.data.all <- MR.data.all[idx, ]
  }else{
    }
  row.names(MR.data.all) <- 1:nrow(MR.data.all)

  MR.data.all$Time<-rep(1:1:length(subset(MR.data.all, Phase == x)$Ox.1), length(levels(MR.data.all$Phase)))
  rm(x)

  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  # Restricting Dataset to Mearusements Taken in the Dark
  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  x<-levels(MR.data.all$Phase)

  if (n.chamber == 1){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Real.Time=chron(), Date=chron(), Time=integer(),
                        Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 2){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Real.Time=chron(), Date=chron(), Time=integer(), Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 3){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Real.Time=chron(), Date=chron(), Time=integer(),
                        Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 4){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Temp.4=numeric(), Ox.4=numeric(), Real.Time=chron(), Date=chron(), Time=integer(),
                        Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 5){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Temp.4=numeric(), Ox.4=numeric(), Temp.5=numeric(), Ox.5=numeric(),
                        Real.Time=chron(), Date=chron(), Time=integer(), Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 6){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Temp.4=numeric(), Ox.4=numeric(), Temp.5=numeric(), Ox.5=numeric(),
                        Temp.6=numeric(), Ox.6=numeric(),
                        Real.Time=chron(), Date=chron(), Time=integer(), Start.Meas=character(), End.Meas=character())
  }

  else if (n.chamber == 7){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Temp.4=numeric(), Ox.4=numeric(), Temp.5=numeric(), Ox.5=numeric(),
                        Temp.6=numeric(), Ox.6=numeric(), Temp.7=numeric(), Ox.7=numeric(),
                        Real.Time=chron(), Date=chron(), Time=integer(), Start.Meas=character(), End.Meas=character())
  }
  else if (n.chamber == 8){
    temp.df<-data.frame(Date.Time=chron(), Phase=factor(), Temp.1=numeric(), Ox.1=numeric(), Temp.2=numeric(), Ox.2=numeric(),
                        Temp.3=numeric(), Ox.3=numeric(), Temp.4=numeric(), Ox.4=numeric(), Temp.5=numeric(), Ox.5=numeric(),
                        Temp.6=numeric(), Ox.6=numeric(), Temp.7=numeric(), Ox.7=numeric(), Temp.8=numeric(), Ox.8=numeric(),
                        Real.Time=chron(), Date=chron(), Time=integer(), Start.Meas=character(), End.Meas=character())
  }
  else{

  }

  for(i in 1:length(x))
  {	x.df<-subset(MR.data.all, Phase==x[i])
  x.start<-rep(as.character(x.df$Real.Time[1]), dim(x.df)[1])
  x.end<-rep(as.character(tail(x.df$Real.Time, 1)), dim(x.df)[1])
  x.df$Start.Meas<-x.start
  x.df$End.Meas<-x.end
  temp.df<-rbind(temp.df, x.df) }

  rm(x)
  rm(i)
  rm(x.df)
  rm(x.start)
  rm(x.end)
  temp.df$Start.Meas<-times(temp.df$Start.Meas)
  temp.df$End.Meas<-times(temp.df$End.Meas)
  temp.df$Total.Phases<-nlevels(temp.df$Phase)


  ### One or two days? (one day error)
  temp.df1<-subset(temp.df, Start.Meas>times(start.measure) & Start.Meas<times(stop.measure))
  if (length(temp.df1$Date) != 0){
    temp.df <- temp.df1
  }
  else
  {
    temp.df2<- subset(temp.df, Start.Meas>times(start.measure) | Start.Meas<times(stop.measure))
    temp.df <- temp.df2
  }

  temp.df$Phase<-factor(temp.df$Phase)
  meas.data <- temp.df
  return(meas.data)
}
