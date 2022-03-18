
QC.activity <- function(clean.data, compare = TRUE){
  Chamber.No<-NULL
  clean.data[is.na(clean.data)] <- 0
  # Temporal function "extract.slope.activity"
  extract.slope.activity <- function(clean.data, r2=0, method = "all"){
  Chamber.No <- Phase <- R2 <- NULL
  MR.est.all<-data.frame(Chamber.No=factor(), Ind=factor(), Mass=numeric(), Volume=numeric(), Date.Time = chron(), Phase=factor(),
                         Temp=numeric(), Slope.with.BR=numeric(), Slope=numeric(), SE=numeric(), R2=numeric())

  chamber<-levels(clean.data$Chamber.No)
  meas<-levels(clean.data$Phase)

  for(i in 1:length(chamber)){
    chamber.df<-subset(clean.data, Chamber.No==chamber[i])
    for(m in 1:length(meas)){
      m.df<-subset(chamber.df, Phase==meas[m])
      model.with.BR <- lm(O2~Time, data=m.df)
      model<-lm(O2.correct~Time, data=m.df)
      out.df<-data.frame(Chamber.No=head(m.df, 1)$Chamber.No, Ind=head(m.df, 1)$Ind, Mass=head(m.df, 1)$Mass, Volume=head(m.df, 1)$Volume,
                         Date.Time = tail(m.df, 1)$Date.Time, Phase=head(m.df, 1)$Phase, Temp=mean(m.df$Temp), Slope.with.BR = coef(model.with.BR)[2],
                         Slope=coef(model)[2],  SE=summary(model)$coef[4], R2=summary(model)$r.squared)
      row.names(out.df)<-NULL
      MR.est.all<-rbind(MR.est.all, out.df)
      }
  }
  head(MR.est.all)
  rm(chamber)
  rm(meas)
  rm(i)
  rm(chamber.df)
  rm(m)
  rm(m.df)
  rm(model)
  rm(out.df)

  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  MR.est<-data.frame(Chamber.No=factor(), Ind=factor(), Mass=numeric(), Volume=numeric(), Phase=factor(), Temp=numeric(),
                     Slope.with.BR=numeric(), Slope=numeric(), SE=numeric(), R2=numeric())

  # I've tweaked the code slightly here to ensure that we're only looking at data which fit a model of linear O2 consumption
  MR.temporal<-subset(MR.est.all, R2>=r2)
  chamber<-levels(MR.temporal$Chamber.No)
  for(i in 1:length(chamber)){
    chamber.df<-subset(MR.temporal, Chamber.No==chamber[i])
    if(method == "all"){
      s<-order(chamber.df$Slope)
      out.df<-chamber.df[s,]
      rm(s)
      row.names(out.df)<-NULL
      MR.est<-rbind(MR.est, out.df)
    }
	else{
	  print("Please, choose an appropriate method from available variants")
	  }
  }

  rm(chamber)
  rm(i)
  rm(chamber.df)
  rm(out.df)
  rm(MR.temporal)

  slope.data <- MR.est
  slope.data$Volume <- as.numeric(as.character(slope.data$Volume))
  slope.data$Mass <- as.numeric(as.character(slope.data$Mass))
  return(slope.data)
}

# Temporal function "calculate.MR.activity"
  calculate.MR.activity  <- function(slope.data, density = 1000){

  BW = slope.data$Mass/1000 # convert Mass from 'g' to 'kg'
  V = slope.data$Volume/1000 - (BW/(density/1000)) #convert Volume from 'mL' to 'L' and Density from 'kg/M^3' to 'kg/L'

  slope.data$MR.abs.with.BR = -(slope.data$Slope.with.BR*V*3600)
  slope.data$MR.mass.with.BR = -(slope.data$Slope.with.BR*V*3600/BW) #3600 sec = 1 hour

  slope.data$BR = (slope.data$Slope.with.BR - slope.data$Slope)/slope.data$Slope.with.BR*100 # in %
  slope.data$MR.abs = -(slope.data$Slope*V*3600)
  slope.data$MR.mass = -(slope.data$Slope*V*3600/BW)

  MR.data <- slope.data
  return(MR.data)
}


# Actual code
  slope.data.all<-extract.slope.activity(clean.data, r2=0, method = "all")
  MR.data.all<-calculate.MR.activity(slope.data.all, density = 1000)
  a <- nlevels(MR.data.all$Chamber.No)

  if(compare == TRUE){
  png(filename=paste0(TMPADRESS, "QC.total.O2.chambers_1.png"),width=1548,height=1160, res=200)
    comparison <- xyplot(MR.mass + MR.mass.with.BR ~ Date.Time, groups=Chamber.No, grid = TRUE,
           data=MR.data.all, main="Activity during the period of measurements",
           type=c("a", "p"), allow.multiple=T, layout=(c(1,2)),
           xlab="Time", ylab = bquote("Mass-specific metabolic rate (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
           strip = strip.custom(factor.levels = c("MO2 after correction for background respiration",
                                                  "MO2 before correction for background respiration")),
           auto.key=list(space="top", columns=4,
                         title="Chambers", cex.title=1, between.columns = 0.5,
                         lines=TRUE, points = FALSE, cex = 0.8),
           par.settings = list(superpose.line = list(col = c("#0080ff", "#ff00ff", "darkgreen", "#ff0000", "orange", "#00ff00", "brown" , "#03c4a1"))))
	print(comparison)
	dev.off()
  }
  else{
    png(filename=paste0(TMPADRESS, "QC.total.O2.chambers_1.png"),width=1548,height=1160, res=200)
    activity <- xyplot(MR.mass ~ Date.Time, groups=Chamber.No, grid = TRUE,
           data=MR.data.all, main="Activity during the period of measurements",
           type=c("a", "p", lwd = 2),
           xlab="Time", ylab = bquote("Mass-specific metabolic rate (" ~ .(DOUNIT) ~ kg^-1 ~ h^-1 ~ ")"),
           auto.key=list(space="top", columns=4, par.strip.text=list(cex=2),
                         title="Chambers", cex.title=1, between.columns = 0.5,
                         lines=TRUE, points = FALSE, cex = 0.8),
           par.settings = list(superpose.line = list(col = c("#0080ff", "#ff00ff", "darkgreen", "#ff0000", "orange", "#00ff00", "brown" , "#03c4a1"))))
	print(activity)
	dev.off()
  }
}
