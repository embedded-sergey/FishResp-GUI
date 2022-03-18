import.test <- function(file, n.chamber = c(1,2,3,4,5,6,7,8),
                        logger = c("AutoResp", "FishResp", "QboxAqua"),
                        meas.to.wait = 0){
# plot.temperature and plot oxygen parameters are located
# in the file: QC.oxygen and QC.temperature, respectively

  V1 <- V2 <- V3 <- V4 <- V5 <- V6 <- V7 <- V8 <- V9 <- V10 <- NULL
  V11 <- V12 <- V13 <- V14 <- V15 <- V16 <- V17 <- V18 <- V19 <- NULL
  V20 <- V21 <- V22 <- V23 <- V24 <- V25 <- V26 <- V27 <- V28 <- NULL
  Phase <- Temp.1 <- Ox.1 <- Chamber.No <- Test <- Time <- Init.O2 <- NULL
  Temp <- O2 <- Temp.2 <- Ox.2 <- Temp.3 <- Ox.3 <- Temp.4 <- Ox.4 <- NULL
  Temp.5 <- Ox.5 <- Temp.6 <- Ox.6 <- Temp.7 <- Ox.7 <- Temp.8 <- Ox.8 <- NULL

  ### AutoResp format ###
  if (logger == "AutoResp"){
    test.data<-read.table(file, sep = "\t", skip=38, header=F, strip.white=T)

    if (n.chamber == 1){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(test.data$Ox.1, na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
    }
    else if (n.chamber == 2){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
    }
    else if (n.chamber == 3){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
    }
    else if (n.chamber == 4){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
    }
    else if (n.chamber == 5){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
    }
    else if (n.chamber == 6){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
    }
    else if (n.chamber == 7){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22, V24, V25))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6, test.data$Ox.7), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
      if(all(is.na(test.data$Ox.7))){test.data$Ox.7[is.na(test.data$Ox.7)] <- aaa}
    }
    else if (n.chamber == 8){
      test.data<-subset(test.data, select=c(V1, V2, V6, V7, V9, V10, V12, V13, V15, V16, V18, V19, V21, V22, V24, V25, V27, V28))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7", "Temp.8", "Ox.8")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6, test.data$Ox.7, test.data$Ox.8), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
      if(all(is.na(test.data$Ox.7))){test.data$Ox.7[is.na(test.data$Ox.7)] <- aaa}
      if(all(is.na(test.data$Ox.8))){test.data$Ox.8[is.na(test.data$Ox.8)] <- aaa}
    }
    else{
      print("Please, choose number of chambers between 1 and 8")
    }
  }


  ### FishResp format ###

  else if (logger == "FishResp"){
    test.data<-read.table(file, sep = "\t", skip=1, header=F, strip.white=T)
    if (n.chamber == 1){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(test.data$Ox.1, na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
    }
    else if (n.chamber == 2){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
    }
    else if (n.chamber == 3){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
    }
    else if (n.chamber == 4){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
    }
    else if (n.chamber == 5){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
    }
    else if (n.chamber == 6){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
    }
    else if (n.chamber == 7){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6, test.data$Ox.7), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
      if(all(is.na(test.data$Ox.7))){test.data$Ox.7[is.na(test.data$Ox.7)] <- aaa}
    }
    else if (n.chamber == 8){
      test.data<-subset(test.data, select=c(V1, V2, V3, V4, V5, V6, V7, V8, V9, V10, V11, V12, V13, V14, V15, V16, V17, V18))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4", "Temp.5", "Ox.5", "Temp.6", "Ox.6", "Temp.7", "Ox.7", "Temp.8", "Ox.8")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(c(test.data$Ox.1, test.data$Ox.2, test.data$Ox.3, test.data$Ox.4,
                   test.data$Ox.5, test.data$Ox.6, test.data$Ox.7, test.data$Ox.8), na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}
      if(all(is.na(test.data$Ox.2))){test.data$Ox.2[is.na(test.data$Ox.2)] <- aaa}
      if(all(is.na(test.data$Ox.3))){test.data$Ox.3[is.na(test.data$Ox.3)] <- aaa}
      if(all(is.na(test.data$Ox.4))){test.data$Ox.4[is.na(test.data$Ox.4)] <- aaa}
      if(all(is.na(test.data$Ox.5))){test.data$Ox.5[is.na(test.data$Ox.5)] <- aaa}
      if(all(is.na(test.data$Ox.6))){test.data$Ox.6[is.na(test.data$Ox.6)] <- aaa}
      if(all(is.na(test.data$Ox.7))){test.data$Ox.7[is.na(test.data$Ox.7)] <- aaa}
      if(all(is.na(test.data$Ox.8))){test.data$Ox.8[is.na(test.data$Ox.8)] <- aaa}
    }
    else{
      print("Please, choose number of chambers between 1 and 8")
    }
  }


  ### QboxAqua format ###

  else if (logger == "QboxAqua"){
    test.data<-read.table(file, sep = ",", skip=2, header=F, strip.white=T)
    if (n.chamber == 1){
      test.data<-subset(test.data, select=c(V1, V9, V4, ncol(test.data)))
      names(test.data)<-c("Time", "Phase", "Temp.1", "Ox.1")
      for(i in 3:ncol(test.data)){test.data[,i] = as.numeric(gsub(',','.', test.data[,i]))}
      aaa <- max(test.data$Ox.1, na.rm = TRUE)
      if(all(is.na(test.data$Ox.1))){test.data$Ox.1[is.na(test.data$Ox.1)] <- aaa}

      ### Indexing measurement phases for QboxAqua
      test.data$Phase[test.data$Phase == "1"] <- "F"
      test.data$Phase[test.data$Phase == "0"] <- "M"
      bdrs <- which(c(FALSE, tail(test.data$Phase,-1) != head(test.data$Phase,-1)))
      bdrs <- paste(bdrs , test.data$Phase[bdrs], sep = "")
      M.start <- grep('M', bdrs, value=TRUE)
      M.start <- as.integer(sub("M$", "", M.start))
      M.end <- grep('F', bdrs, value=TRUE)
      M.end <- as.integer(sub("F$", "", M.end)) - 1
      if(M.end[1] < M.start[1]){M.end <- M.end[-1]}

      for(i in 1:length(M.end)){
        test.data$Phase[M.start[i]:M.end[i]] <- paste(test.data$Phase[M.start[i]:M.end[i]], i, sep = "")
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
  test.data <- subset(test.data, Phase == "M1")
  test.data$Phase <- factor(test.data$Phase)

  # cut off first n raws from 'M' phase
  if(meas.to.wait != 0){
    idx <- unlist(tapply(1:nrow(test.data), test.data$Phase, tail, -(meas.to.wait)), use.names=FALSE)
    test.data <- test.data[idx, ]
  }else{
    }

  if(n.chamber == 1){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
	return(test.data)
  }

  else if(n.chamber == 2){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))
    test.data<-rbind(test.CH1, test.CH2)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }

  else if(n.chamber == 3){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))
    test.data<-rbind(test.CH1, test.CH2, test.CH3)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }

  else if(n.chamber == 4){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH4<-subset(test.data, select=c(Temp.4, Ox.4))
    row.names(test.CH4)<-NULL
    names(test.CH4)<-c("Temp", "O2")
    test.CH4$Chamber.No<-as.factor(rep("CH4", dim(test.CH4)[1]))
    test.CH4$Test<-as.factor(rep("test", dim(test.CH4)[1]))
    test.CH4$Time<-1:dim(test.CH4)[1]
    test.CH4$Init.O2<-rep(mean(test.CH4$O2[1]), dim(test.CH4)[1])
    test.CH4<-subset(test.CH4, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1, test.CH2, test.CH3, test.CH4)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }

  else if(n.chamber == 5){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH4<-subset(test.data, select=c(Temp.4, Ox.4))
    row.names(test.CH4)<-NULL
    names(test.CH4)<-c("Temp", "O2")
    test.CH4$Chamber.No<-as.factor(rep("CH4", dim(test.CH4)[1]))
    test.CH4$Test<-as.factor(rep("test", dim(test.CH4)[1]))
    test.CH4$Time<-1:dim(test.CH4)[1]
    test.CH4$Init.O2<-rep(mean(test.CH4$O2[1]), dim(test.CH4)[1])
    test.CH4<-subset(test.CH4, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH5<-subset(test.data, select=c(Temp.5, Ox.5))
    row.names(test.CH5)<-NULL
    names(test.CH5)<-c("Temp", "O2")
    test.CH5$Chamber.No<-as.factor(rep("CH5", dim(test.CH5)[1]))
    test.CH5$Test<-as.factor(rep("test", dim(test.CH5)[1]))
    test.CH5$Time<-1:dim(test.CH5)[1]
    test.CH5$Init.O2<-rep(mean(test.CH5$O2[1]), dim(test.CH5)[1])
    test.CH5<-subset(test.CH5, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1, test.CH2, test.CH3, test.CH4, test.CH5)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }

  else if(n.chamber == 6){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH4<-subset(test.data, select=c(Temp.4, Ox.4))
    row.names(test.CH4)<-NULL
    names(test.CH4)<-c("Temp", "O2")
    test.CH4$Chamber.No<-as.factor(rep("CH4", dim(test.CH4)[1]))
    test.CH4$Test<-as.factor(rep("test", dim(test.CH4)[1]))
    test.CH4$Time<-1:dim(test.CH4)[1]
    test.CH4$Init.O2<-rep(mean(test.CH4$O2[1]), dim(test.CH4)[1])
    test.CH4<-subset(test.CH4, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH5<-subset(test.data, select=c(Temp.5, Ox.5))
    row.names(test.CH5)<-NULL
    names(test.CH5)<-c("Temp", "O2")
    test.CH5$Chamber.No<-as.factor(rep("CH5", dim(test.CH5)[1]))
    test.CH5$Test<-as.factor(rep("test", dim(test.CH5)[1]))
    test.CH5$Time<-1:dim(test.CH5)[1]
    test.CH5$Init.O2<-rep(mean(test.CH5$O2[1]), dim(test.CH5)[1])
    test.CH5<-subset(test.CH5, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH6<-subset(test.data, select=c(Temp.6, Ox.6))
    row.names(test.CH6)<-NULL
    names(test.CH6)<-c("Temp", "O2")
    test.CH6$Chamber.No<-as.factor(rep("CH6", dim(test.CH6)[1]))
    test.CH6$Test<-as.factor(rep("test", dim(test.CH6)[1]))
    test.CH6$Time<-1:dim(test.CH6)[1]
    test.CH6$Init.O2<-rep(mean(test.CH6$O2[1]), dim(test.CH6)[1])
    test.CH6<-subset(test.CH6, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1, test.CH2, test.CH3, test.CH4, test.CH5, test.CH6)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }


  else if(n.chamber == 7){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH4<-subset(test.data, select=c(Temp.4, Ox.4))
    row.names(test.CH4)<-NULL
    names(test.CH4)<-c("Temp", "O2")
    test.CH4$Chamber.No<-as.factor(rep("CH4", dim(test.CH4)[1]))
    test.CH4$Test<-as.factor(rep("test", dim(test.CH4)[1]))
    test.CH4$Time<-1:dim(test.CH4)[1]
    test.CH4$Init.O2<-rep(mean(test.CH4$O2[1]), dim(test.CH4)[1])
    test.CH4<-subset(test.CH4, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH5<-subset(test.data, select=c(Temp.5, Ox.5))
    row.names(test.CH5)<-NULL
    names(test.CH5)<-c("Temp", "O2")
    test.CH5$Chamber.No<-as.factor(rep("CH5", dim(test.CH5)[1]))
    test.CH5$Test<-as.factor(rep("test", dim(test.CH5)[1]))
    test.CH5$Time<-1:dim(test.CH5)[1]
    test.CH5$Init.O2<-rep(mean(test.CH5$O2[1]), dim(test.CH5)[1])
    test.CH5<-subset(test.CH5, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH6<-subset(test.data, select=c(Temp.6, Ox.6))
    row.names(test.CH6)<-NULL
    names(test.CH6)<-c("Temp", "O2")
    test.CH6$Chamber.No<-as.factor(rep("CH6", dim(test.CH6)[1]))
    test.CH6$Test<-as.factor(rep("test", dim(test.CH6)[1]))
    test.CH6$Time<-1:dim(test.CH6)[1]
    test.CH6$Init.O2<-rep(mean(test.CH6$O2[1]), dim(test.CH6)[1])
    test.CH6<-subset(test.CH6, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH7<-subset(test.data, select=c(Temp.7, Ox.7))
    row.names(test.CH7)<-NULL
    names(test.CH7)<-c("Temp", "O2")
    test.CH7$Chamber.No<-as.factor(rep("CH7", dim(test.CH7)[1]))
    test.CH7$Test<-as.factor(rep("test", dim(test.CH7)[1]))
    test.CH7$Time<-1:dim(test.CH7)[1]
    test.CH7$Init.O2<-rep(mean(test.CH7$O2[1]), dim(test.CH7)[1])
    test.CH7<-subset(test.CH7, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1, test.CH2, test.CH3, test.CH4, test.CH5, test.CH6, test.CH7)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }


  else if(n.chamber == 8){
    test.CH1<-subset(test.data, select=c(Temp.1, Ox.1))
    row.names(test.CH1)<-NULL
    names(test.CH1)<-c("Temp", "O2")
    test.CH1$Chamber.No<-as.factor(rep("CH1", dim(test.CH1)[1]))
    test.CH1$Test<-as.factor(rep("test", dim(test.CH1)[1]))
    test.CH1$Time<-1:dim(test.CH1)[1]
    test.CH1$Init.O2<-rep(mean(test.CH1$O2[1]), dim(test.CH1)[1])
    test.CH1<-subset(test.CH1, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH2<-subset(test.data, select=c(Temp.2, Ox.2))
    row.names(test.CH2)<-NULL
    names(test.CH2)<-c("Temp", "O2")
    test.CH2$Chamber.No<-as.factor(rep("CH2", dim(test.CH2)[1]))
    test.CH2$Test<-as.factor(rep("test", dim(test.CH2)[1]))
    test.CH2$Time<-1:dim(test.CH2)[1]
    test.CH2$Init.O2<-rep(mean(test.CH2$O2[1]), dim(test.CH2)[1])
    test.CH2<-subset(test.CH2, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH3<-subset(test.data, select=c(Temp.3, Ox.3))
    row.names(test.CH3)<-NULL
    names(test.CH3)<-c("Temp", "O2")
    test.CH3$Chamber.No<-as.factor(rep("CH3", dim(test.CH3)[1]))
    test.CH3$Test<-as.factor(rep("test", dim(test.CH3)[1]))
    test.CH3$Time<-1:dim(test.CH3)[1]
    test.CH3$Init.O2<-rep(mean(test.CH3$O2[1]), dim(test.CH3)[1])
    test.CH3<-subset(test.CH3, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH4<-subset(test.data, select=c(Temp.4, Ox.4))
    row.names(test.CH4)<-NULL
    names(test.CH4)<-c("Temp", "O2")
    test.CH4$Chamber.No<-as.factor(rep("CH4", dim(test.CH4)[1]))
    test.CH4$Test<-as.factor(rep("test", dim(test.CH4)[1]))
    test.CH4$Time<-1:dim(test.CH4)[1]
    test.CH4$Init.O2<-rep(mean(test.CH4$O2[1]), dim(test.CH4)[1])
    test.CH4<-subset(test.CH4, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH5<-subset(test.data, select=c(Temp.5, Ox.5))
    row.names(test.CH5)<-NULL
    names(test.CH5)<-c("Temp", "O2")
    test.CH5$Chamber.No<-as.factor(rep("CH5", dim(test.CH5)[1]))
    test.CH5$Test<-as.factor(rep("test", dim(test.CH5)[1]))
    test.CH5$Time<-1:dim(test.CH5)[1]
    test.CH5$Init.O2<-rep(mean(test.CH5$O2[1]), dim(test.CH5)[1])
    test.CH5<-subset(test.CH5, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH6<-subset(test.data, select=c(Temp.6, Ox.6))
    row.names(test.CH6)<-NULL
    names(test.CH6)<-c("Temp", "O2")
    test.CH6$Chamber.No<-as.factor(rep("CH6", dim(test.CH6)[1]))
    test.CH6$Test<-as.factor(rep("test", dim(test.CH6)[1]))
    test.CH6$Time<-1:dim(test.CH6)[1]
    test.CH6$Init.O2<-rep(mean(test.CH6$O2[1]), dim(test.CH6)[1])
    test.CH6<-subset(test.CH6, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH7<-subset(test.data, select=c(Temp.7, Ox.7))
    row.names(test.CH7)<-NULL
    names(test.CH7)<-c("Temp", "O2")
    test.CH7$Chamber.No<-as.factor(rep("CH7", dim(test.CH7)[1]))
    test.CH7$Test<-as.factor(rep("test", dim(test.CH7)[1]))
    test.CH7$Time<-1:dim(test.CH7)[1]
    test.CH7$Init.O2<-rep(mean(test.CH7$O2[1]), dim(test.CH7)[1])
    test.CH7<-subset(test.CH7, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.CH8<-subset(test.data, select=c(Temp.8, Ox.8))
    row.names(test.CH8)<-NULL
    names(test.CH8)<-c("Temp", "O2")
    test.CH8$Chamber.No<-as.factor(rep("CH8", dim(test.CH8)[1]))
    test.CH8$Test<-as.factor(rep("test", dim(test.CH8)[1]))
    test.CH8$Time<-1:dim(test.CH8)[1]
    test.CH8$Init.O2<-rep(mean(test.CH8$O2[1]), dim(test.CH8)[1])
    test.CH8<-subset(test.CH8, select=c(Chamber.No, Test, Time, Init.O2, Temp, O2))

    test.data<-rbind(test.CH1, test.CH2, test.CH3, test.CH4, test.CH5, test.CH6, test.CH7, test.CH8)
    test.data$delta.O2<-test.data$O2 - test.data$Init.O2
    return(test.data)
  }
}
