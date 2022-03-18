pyroscience.aquaresp <- function(pyroscience.file,
                                 aquaresp.file,
                                 fishresp.file,
                                 n.chamber = c(1,2,3,4),
                                 date.format = c("DMY", "MDY", "YMD"),
                                 wait.phase = NA, measure.phase = NA){

  # Loading PyroScience data
  V1 <- V2 <- V3 <- V4 <- V5 <- V6 <- V7 <- V8 <- V9 <- V10 <- V11 <- V12 <- NULL

  if (n.chamber == 1){
    pyroscience.data <- read.table(pyroscience.file, sep = "\t", skip=14, header=F, strip.white=T)
    pyroscience.data <- subset(pyroscience.data, select=c(V1, V2, V9, V5))
    names(pyroscience.data)<-c("Date", "Time", "Temp.1", "Ox.1")
    pyroscience.data$Date.Time <- paste(pyroscience.data$Date, pyroscience.data$Time, sep="/")
    pyroscience.data$Phase <- "F"
    pyroscience.data[pyroscience.data == "---"] <- NA
    pyroscience.data <- pyroscience.data[,c(5,6,3,4)]
    }

  else if (n.chamber == 2){
    pyroscience.data <- read.table(pyroscience.file, sep = "\t", skip=16, header=F, strip.white=T)
    pyroscience.data <- subset(pyroscience.data, select=c(V1, V2, V9, V5, V10, V6))
    colnames(pyroscience.data)<-c("Date", "Time", "Temp.1", "Ox.1", "Temp.2", "Ox.2")
    pyroscience.data$Date.Time <- paste(pyroscience.data$Date, pyroscience.data$Time, sep="/")
    pyroscience.data$Phase <- "F"
    pyroscience.data[pyroscience.data == "---"] <- NA
    pyroscience.data <- pyroscience.data[,c(7,8,3,4,5,6)]
    }

  else if (n.chamber == 3){
    pyroscience.data <- read.table(pyroscience.file, sep = "\t", skip=18, header=F, strip.white=T)
    pyroscience.data <- subset(pyroscience.data, select=c(V1, V2, V9, V5, V10, V6, V11, V7))
    colnames(pyroscience.data)<-c("Date", "Time", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3")
    pyroscience.data$Date.Time <- paste(pyroscience.data$Date, pyroscience.data$Time, sep="/")
    pyroscience.data$Phase <- "F"
    pyroscience.data[pyroscience.data == "---"] <- NA
    pyroscience.data <- pyroscience.data[,c(9,10,3,4,5,6,7,8)]
    }

  else if (n.chamber == 4){
    pyroscience.data <- read.table(pyroscience.file, sep = "\t", skip=20, header=F, strip.white=T)
    pyroscience.data <- subset(pyroscience.data, select=c(V1, V2, V9, V5, V10, V6, V11, V7, V12, V8))
    colnames(pyroscience.data)<-c("Date", "Time", "Temp.1", "Ox.1", "Temp.2", "Ox.2", "Temp.3", "Ox.3", "Temp.4", "Ox.4")
    pyroscience.data$Date.Time <- paste(pyroscience.data$Date, pyroscience.data$Time, sep="/")
    pyroscience.data$Phase <- "F"
    pyroscience.data[pyroscience.data == "---"] <- NA
    pyroscience.data <- pyroscience.data[,c(11,12,3,4,5,6,7,8,9,10)]
    }

  else{
    print("Please, choose the number of chambers between 1 and 4")
    }


  if(any(date.format == "DMY")){
    pyroscience.data$Date.Time<-strptime(as.character(pyroscience.data$Date.Time), "%d/%m/%Y/%H:%M:%S")
    ts<-times(strftime(pyroscience.data$Date.Time, "%H:%M:%S"))
    ds <- format(pyroscience.data$Date.Time, format="%d/%m/%Y")
    pyroscience.data$Date.Time <- paste(ds,ts, sep = "/")
    }

  else if(any(date.format == "MDY")){
    pyroscience.data$Date.Time<-strptime(as.character(pyroscience.data$Date.Time), "%m/%d/%Y/%H:%M:%S")
    ts<-times(strftime(pyroscience.data$Date.Time, "%H:%M:%S"))
    ds <- format(pyroscience.data$Date.Time, format="%m/%d/%Y")
    pyroscience.data$Date.Time <- paste(ds,ts, sep = "/")
    }

  else if(any(date.format == "YMD")){
    pyroscience.data$Date.Time<-strptime(as.character(pyroscience.data$Date.Time), "%Y/%m/%d/%H:%M:%S")
    ts<-times(strftime(pyroscience.data$Date.Time, "%H:%M:%S"))
    ds <- format(pyroscience.data$Date.Time, format="%Y/%m/%d")
    pyroscience.data$Date.Time <- paste(ds,ts, sep = "/")
    }


  # Loading AquaResp data
  aquaresp.data <- read.table(aquaresp.file, sep = ";", skip=16, header=F, strip.white=T)
  aquaresp.data <- subset(aquaresp.data, select=V1)
  colnames(aquaresp.data) <- "Date.Time"
  aquaresp.data$Date.Time<-strptime(as.character(aquaresp.data$Date.Time), "%Y-%m-%d %H:%M:%S")
  tms<-times(strftime(aquaresp.data$Date.Time, "%H:%M:%S"))

  if(any(date.format == "DMY")){
    dts <- format(aquaresp.data$Date.Time, format="%d/%m/%Y")
    x <- paste(dts,tms, sep = "/")
    }

  else if(any(date.format == "MDY")){
    dts <- format(aquaresp.data$Date.Time, format="%m/%d/%Y")
    x <- paste(dts,tms, sep = "/")
    }

  else if(any(date.format == "YMD")){
    dts <- format(aquaresp.data$Date.Time, format="%Y/%m/%d")
    x <- paste(dts,tms, sep = "/")
    }

  else{
    print("Please, choose the date format: DMY, MDY or YMD, where D-day, M-month, Y-year")
    }

  # Join datasets
  m <- 1 #counter

  for(i in x){
    a <- which(pyroscience.data$Date.Time == i)
    if(length(a) == 0){
      a1 <- substring(i, 1, 18)
      a2 <- as.numeric(substring(i, 19))
      if(a2 >= 9){
        a2 <- a2 - 1
        }
      else{
        a2 <- a2 + 1
        }
      b <- paste(a1, a2, sep = "")
      a <- which(pyroscience.data$Date.Time == b)
      }
    else{
      }
    if(length(pyroscience.data$Date.Time) - a > measure.phase){
      pyroscience.data$Phase[a:(a + (measure.phase-1))] <- paste("M", m, sep = "")
      }
    else{
      }
    if(a - wait.phase > 0){
      pyroscience.data$Phase[(a - wait.phase):(a-1)] <- paste("W", m, sep = "")
      }
    else{
      }
      m = m + 1
    }

  write.table(pyroscience.data, file = fishresp.file, sep = "\t", row.names = F)
  }
