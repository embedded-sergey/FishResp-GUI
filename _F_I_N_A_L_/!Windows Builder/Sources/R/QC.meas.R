
QC.meas <- function(clean.data,
                    QC = c("Temperature", "Total.O2.phases", "Corrected.O2.phases",
                           "Total.O2.chambers", "Corrected.O2.chambers")){

  if (QC == "Temperature"){
  	png(filename=paste0(TMPADRESS, "QC.temperature_1.png"),width=1548,height=1160, pointsize = 30)
    print(xyplot(Temp~Time|Phase*Chamber.No, data=clean.data,
	            par.strip.text=list(cex=0.8), cex=0.8,
                xlab = "Time (s)", ylab = bquote("Temperature (" ~ C^o ~ ")"), as.table = T,
                scales=list(tck=c(1,0), x=list(cex=0.8), y=list(cex=0.8))))
	dev.off()
  }

    else if (QC == "Total.O2.phases"){
    png(filename=paste0(TMPADRESS, "QC.total.O2.phases_1.png"),width=1548,height=1160, pointsize = 30)
    print(xyplot(O2~Time|Phase*Chamber.No, data=clean.data,
                  par.strip.text=list(cex=0.8), cex=0.8,
                  xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), as.table = T,
                  scales=list(tck=c(1,0), x=list(cex=0.8), y=list(cex=0.8))))
	dev.off()
  }

    else if (QC == "Corrected.O2.phases"){
    png(filename=paste0(TMPADRESS, "QC.corrected.O2.phases_1.png"),width=1548,height=1160, pointsize = 30)
    print(xyplot(O2.correct~Time|Phase*Chamber.No, data=clean.data,
                  par.strip.text=list(cex=0.8), cex=0.8,
                  xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), as.table = T,
                  scales=list(tck=c(1,0), x=list(cex=0.8), y=list(cex=0.8))))
	dev.off()
  }

  else if (QC == "Total.O2.chambers"){
    png(filename=paste0(TMPADRESS, "QC.total.O2.chambers_1.png"),width=1548,height=1160, pointsize = 30)
    print(xyplot(O2~Date.Time|Chamber.No, data=clean.data, cex=0.8,
                  xlab = "Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), as.table = T))
	dev.off()
  }

  else if (QC == "Corrected.O2.chambers"){
    png(filename=paste0(TMPADRESS, "QC.corrected.O2.chambers_1.png"),width=1548,height=1160, pointsize = 30)
    print(xyplot(O2.correct~Date.Time|Chamber.No, data=clean.data, cex=0.8,
                  xlab = "Time", ylab = paste("DO (", DOUNIT, "/L)", sep = ""), as.table = T))
	dev.off()
  }



  else
  {
    print ("This quality test does not exist! Please, choose among avaliable variants: Temperature, Total.O2.chambers, Total.O2.phases, Corrected.O2.chambers or Corrected.O2.phases")
  }
}
