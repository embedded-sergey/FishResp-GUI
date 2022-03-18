QC.oxygen.meas <- function(meas.data){
    if(ncol(meas.data) == 10){
	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
	}
    if(ncol(meas.data) == 12){
	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(2,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 14){
	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(3,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 16){
   	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(4,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.4~meas.data$Date.Time, main="Chamber 4", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 18){
   	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(4,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.4~meas.data$Date.Time, main="Chamber 4", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
	  png(filename=paste0(TMPADRESS, "meas.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	  par(mfrow=c(4,1))
	  plot(meas.data$Ox.5~meas.data$Date.Time, main="Chamber 5", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 20){
   	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(4,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.4~meas.data$Date.Time, main="Chamber 4", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
	  png(filename=paste0(TMPADRESS, "meas.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	  par(mfrow=c(4,1))
	  plot(meas.data$Ox.5~meas.data$Date.Time, main="Chamber 5", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.6~meas.data$Date.Time, main="Chamber 6", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 22){
   	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(4,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.4~meas.data$Date.Time, main="Chamber 4", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
	  png(filename=paste0(TMPADRESS, "meas.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	  par(mfrow=c(4,1))
	  plot(meas.data$Ox.5~meas.data$Date.Time, main="Chamber 5", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.6~meas.data$Date.Time, main="Chamber 6", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(meas.data$Ox.7~meas.data$Date.Time, main="Chamber 7", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
   if(ncol(meas.data) == 24){
   	  png(filename=paste0(TMPADRESS, "meas.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      par(mfrow=c(4,1))
      plot(meas.data$Ox.1~meas.data$Date.Time, main="Chamber 1", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.2~meas.data$Date.Time, main="Chamber 2", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.3~meas.data$Date.Time, main="Chamber 3", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.4~meas.data$Date.Time, main="Chamber 4", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
	  png(filename=paste0(TMPADRESS, "meas.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	  par(mfrow=c(4,1))
	  plot(meas.data$Ox.5~meas.data$Date.Time, main="Chamber 5", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(meas.data$Ox.6~meas.data$Date.Time, main="Chamber 6", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(meas.data$Ox.7~meas.data$Date.Time, main="Chamber 7", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(meas.data$Ox.8~meas.data$Date.Time, main="Chamber 8", xlab = "Date and Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  dev.off()
    }
  }