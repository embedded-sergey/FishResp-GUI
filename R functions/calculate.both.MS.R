calculate.both.MS  <- function(slope.data.1, slope.data.2, density = 1000, simplify = TRUE, plot.BR = TRUE,
                          plot.MR.abs = TRUE, plot.MR.mass = TRUE){

  BW = slope.data.1$Mass/1000 # convert Mass from 'g' to 'kg'
  V = slope.data.1$Volume/1000 - (BW/(density/1000)) #convert Volume from 'mL' to 'L' and Density from 'kg/M^3' to 'kg/L'

  slope.data.1$MR.abs.with.BR = -(slope.data.1$Slope.with.BR*V*3600) #3600 sec = 1 hour

  slope.data.1$BR = (slope.data.1$Slope.with.BR - slope.data.1$Slope)/slope.data.1$Slope.with.BR*100 # in %
  slope.data.1$MR.abs = -(slope.data.1$Slope*V*3600)
  slope.data.1$MR.mass = -(slope.data.1$Slope*V*3600/BW)

  a <- xyplot(BR~Temp|Ind, data=slope.data.1, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = "Background respiration (%)",
              main = paste0(Trait.1.name, ": ", "Percentage rate of background respiration"))
  b <- xyplot(MR.abs~Temp|Ind, data=slope.data.1, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Absolute MR (" ~ .(DOUNIT) ~ h^-1 ~ ")"),
              main = paste0(Trait.1.name, ": ", "Absolute metabolic rate"))
  d <- xyplot(MR.mass~Temp|Ind, data=slope.data.1, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Mass-specific MR (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
              main = paste0(Trait.1.name, ": ", "Mass-specific metabolic rate"))

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

  MR.data.1 <- slope.data.1
  MR.data.1$DO.unit <- as.character(DOUNIT)

  colnames(MR.data.1)[5] <- paste(Trait.1.name, "Date.Time", sep = "_")
  colnames(MR.data.1)[6] <- paste(Trait.1.name, "Phase", sep = "_")
  colnames(MR.data.1)[7] <- paste(Trait.1.name, "Temp", sep = "_")
  colnames(MR.data.1)[8] <- paste(Trait.1.name, "Slope.with.BR", sep = "_")
  colnames(MR.data.1)[9] <- paste(Trait.1.name, "Slope", sep = "_")
  colnames(MR.data.1)[10] <- paste(Trait.1.name, "SE", sep = "_")
  colnames(MR.data.1)[11] <- paste(Trait.1.name, "R2", sep = "_")
  colnames(MR.data.1)[12] <- paste(Trait.1.name, "MR.mass.with.BR", sep = "_")
  colnames(MR.data.1)[13] <- paste(Trait.1.name, "BR", sep = "_")
  colnames(MR.data.1)[14] <- paste(Trait.1.name, "MR.abs", sep = "_")
  colnames(MR.data.1)[15] <- paste(Trait.1.name, "MR.mass", sep = "_")

  BW = slope.data.2$Mass/1000 # convert Mass from 'g' to 'kg'
  V = slope.data.2$Volume/1000 - (BW/(density/1000)) #convert Volume from 'mL' to 'L' and Density from 'kg/M^3' to 'kg/L'

  slope.data.2$MR.abs.with.BR = -(slope.data.2$Slope.with.BR*V*3600) #3600 sec = 1 hour

  slope.data.2$BR = (slope.data.2$Slope.with.BR - slope.data.2$Slope)/slope.data.2$Slope.with.BR*100 # in %
  slope.data.2$MR.abs = -(slope.data.2$Slope*V*3600)
  slope.data.2$MR.mass = -(slope.data.2$Slope*V*3600/BW)

  a <- xyplot(BR~Temp|Ind, data=slope.data.2, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = "Background respiration (%)",
              main = paste0(Trait.2.name, ": ", "Percentage rate of background respiration"))
  b <- xyplot(MR.abs~Temp|Ind, data=slope.data.2, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Absolute MR (" ~ .(DOUNIT) ~ h^-1 ~ ")"),
              main = paste0(Trait.2.name, ": ", "Absolute metabolic rate"))
  d <- xyplot(MR.mass~Temp|Ind, data=slope.data.2, as.table = T,
              xlab = bquote("Temperature (" ~ C^o ~ ")"), ylab = bquote("Mass-specific MR (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
              main = paste0(Trait.2.name, ": ", "Mass-specific metabolic rate"))

  if (plot.BR == TRUE){
    png(filename=paste0(TMPADRESS, "Results_4.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(a)
	dev.off()
  }

  if (plot.MR.abs == TRUE){
    png(filename=paste0(TMPADRESS, "Results_5.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(b)
    dev.off()
  }

  if (plot.MR.mass == TRUE){
    png(filename=paste0(TMPADRESS, "Results_6.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(d)
	dev.off()
  }

    MR.data.2 <- slope.data.2
    MR.data.2$DO.unit <- as.character(DOUNIT)

    colnames(MR.data.2)[5] <- paste(Trait.2.name, "Date.Time", sep = "_")
    colnames(MR.data.2)[6] <- paste(Trait.2.name, "Phase", sep = "_")
    colnames(MR.data.2)[7] <- paste(Trait.2.name, "Temp", sep = "_")
    colnames(MR.data.2)[8] <- paste(Trait.2.name, "Slope.with.BR", sep = "_")
    colnames(MR.data.2)[9] <- paste(Trait.2.name, "Slope", sep = "_")
    colnames(MR.data.2)[10] <- paste(Trait.2.name, "SE", sep = "_")
    colnames(MR.data.2)[11] <- paste(Trait.2.name, "R2", sep = "_")
    colnames(MR.data.2)[12] <- paste(Trait.2.name, "MR.mass.with.BR", sep = "_")
    colnames(MR.data.2)[13] <- paste(Trait.2.name, "BR", sep = "_")
    colnames(MR.data.2)[14] <- paste(Trait.2.name, "MR.abs", sep = "_")
    colnames(MR.data.2)[15] <- paste(Trait.2.name, "MR.mass", sep = "_")


	final.data <- merge(MR.data.1, MR.data.2)


      final.data$MS.abs <- final.data[,26] - final.data[,15]
      final.data$MS.mass <- final.data[,27] - final.data[,16]
      final.data$MS.fact <- final.data[,26] / final.data[,15]

      a <- bwplot(MS.abs~final.data[,18]|Ind, data=final.data, as.table = T,
                  xlab = "Measurement phase",
                  ylab = bquote("Absolute MS (" ~ .(DOUNIT) ~ h^-1 ~ ")"),
                  main = "Absolute metabolic scope")
      b <- bwplot(MS.mass~final.data[,18]|Ind, data=final.data, as.table = T,
                  xlab = "Measurement phase",
                  ylab = bquote("Mass-specific MS (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
                  main = "Mass-specific metabolic scope")
      d <- bwplot(MS.fact~final.data[,18]|Ind, data=final.data, as.table = T,
                  xlab = "Measurement phase",
                  ylab = "Factorial MS (coefficient)",
                  main = "Factorial metabolic scope")


    png(filename=paste0(TMPADRESS, "Results_7.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(a)
	dev.off()

	png(filename=paste0(TMPADRESS, "Results_8.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(b)
	dev.off()

	png(filename=paste0(TMPADRESS, "Results_9.png"),width=1548,height=1160, res=200)
    par(mfrow = c(2, 1))
    print(d)
	dev.off()

      if(simplify == TRUE){
        final.data <- final.data[,c(1,2,3,4,5,8,12,14,15,16,19,
                                    23,25,26,27,28,29,30)]
      }
      else{}

      return(final.data)
}
