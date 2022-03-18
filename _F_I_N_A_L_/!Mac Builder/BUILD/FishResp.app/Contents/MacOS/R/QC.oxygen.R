QC.oxygen <- function(test.data){
    if(nlevels(test.data$Chamber.No) == 1){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 2){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(2,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 3){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(3,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 4){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH4"]~test.data$Time[test.data$Chamber.No == "CH4"], main="Chamber 4", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 5){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH4"]~test.data$Time[test.data$Chamber.No == "CH4"], main="Chamber 4", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	png(filename=paste0(TMPADRESS, "test.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH5"]~test.data$Time[test.data$Chamber.No == "CH5"], main="Chamber 5", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 6){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH4"]~test.data$Time[test.data$Chamber.No == "CH4"], main="Chamber 4", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	png(filename=paste0(TMPADRESS, "test.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH5"]~test.data$Time[test.data$Chamber.No == "CH5"], main="Chamber 5", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH6"]~test.data$Time[test.data$Chamber.No == "CH6"], main="Chamber 6", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 7){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH4"]~test.data$Time[test.data$Chamber.No == "CH4"], main="Chamber 4", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	png(filename=paste0(TMPADRESS, "test.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH5"]~test.data$Time[test.data$Chamber.No == "CH5"], main="Chamber 5", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH6"]~test.data$Time[test.data$Chamber.No == "CH6"], main="Chamber 6", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH7"]~test.data$Time[test.data$Chamber.No == "CH7"], main="Chamber 7", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }

    if(nlevels(test.data$Chamber.No) == 8){
	png(filename=paste0(TMPADRESS, "test.oxygen_1.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH1"]~test.data$Time[test.data$Chamber.No == "CH1"], main="Chamber 1", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH2"]~test.data$Time[test.data$Chamber.No == "CH2"], main="Chamber 2", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
      plot(test.data$O2[test.data$Chamber.No == "CH3"]~test.data$Time[test.data$Chamber.No == "CH3"], main="Chamber 3", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH4"]~test.data$Time[test.data$Chamber.No == "CH4"], main="Chamber 4", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	png(filename=paste0(TMPADRESS, "test.oxygen_2.png"),width=1548,height=1160, pointsize = 30)
	par(mfrow=c(4,1))
      plot(test.data$O2[test.data$Chamber.No == "CH5"]~test.data$Time[test.data$Chamber.No == "CH5"], main="Chamber 5", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH6"]~test.data$Time[test.data$Chamber.No == "CH6"], main="Chamber 6", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH7"]~test.data$Time[test.data$Chamber.No == "CH7"], main="Chamber 7", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	  plot(test.data$O2[test.data$Chamber.No == "CH8"]~test.data$Time[test.data$Chamber.No == "CH8"], main="Chamber 8", xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), col = "#0082FF", cex=0.8)
	dev.off()
	  }
}
