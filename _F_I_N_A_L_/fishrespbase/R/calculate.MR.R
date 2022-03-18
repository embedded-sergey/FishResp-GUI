calculate.MR  <- function(slope.data, density = 1000, simplify = TRUE, plot.BR = TRUE,
                          plot.MR.abs = TRUE, plot.MR.mass = TRUE){

  BW = slope.data$Mass/1000 # convert Mass from 'g' to 'kg'
  V = slope.data$Volume/1000 - (BW/(density/1000)) #convert Volume from 'mL' to 'L' and Density from 'kg/M^3' to 'kg/L'

  slope.data$MR.abs.with.BR = -(slope.data$Slope.with.BR*V*3600) #3600 sec = 1 hour

  slope.data$BR = (slope.data$Slope.with.BR - slope.data$Slope)/slope.data$Slope.with.BR*100 # in %
  slope.data$MR.abs = -(slope.data$Slope*V*3600)
  slope.data$MR.mass = -(slope.data$Slope*V*3600/BW)

  a <- xyplot(BR~Temp|Ind, data=slope.data, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = "Background respiration (%)",
              main = "Percentage rate of background respiration")
  b <- xyplot(MR.abs~Temp|Ind, data=slope.data, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Absolute MR (" ~ .(DOUNIT) ~ h^-1 ~ ")"),
              main = "Absolute metabolic rate")
  d <- xyplot(MR.mass~Temp|Ind, data=slope.data, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Mass-specific MR (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
              main = "Mass-specific metabolic rate")

  if (plot.BR == TRUE){
    png(filename=paste0(TMPADRESS, "Results_1.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(a)
	  dev.off()
  }

  if (plot.MR.abs == TRUE){
    png(filename=paste0(TMPADRESS, "Results_2.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(b)
    dev.off()
  }

  if (plot.MR.mass == TRUE){
    png(filename=paste0(TMPADRESS, "Results_3.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(d)
	  dev.off()
  }

  MR.data <- slope.data
  MR.data$DO.unit <- as.character(DOUNIT)
  if(simplify == TRUE){
      MR.data <- MR.data[,c(1,2,3,4,16,7,11,13,14,15)]
    }
    else{
	  MR.data <- MR.data[,c(1,2,3,4,16,5,6,7,8,9,10,11,12,13,14,15)]
	}

  return(MR.data)
}
