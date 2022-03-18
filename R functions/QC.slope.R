QC.slope <- function(slope.data, clean.data,
                     chamber = c("CH1", "CH2", "CH3", "CH4",
                                 "CH5", "CH6", "CH7", "CH8"),
					           current = 9999, alter = 9999, residuals =F){

  #==================================================================================================================================================#
  ### Plotting Raw Data -- subsetting those values with the minimal oxygen consumption (i.e. lowest resting metabolic rate)
  #--------------------------------------------------------------------------------------------------------------------------------------------------#
  Chamber.No <- Phase <- m1.df <- m2.df <- Time <- NULL
  clean.data[is.na(clean.data)] <- 0
  chlevels<-levels(slope.data$Chamber.No)
  extracted.data<-data.frame(Date.Time=chron(), Date=chron(), Real.Time=times(), Time=integer(), Phase=factor(), Start.Meas=times(), End.Meas=times(),
                             Chamber.No=factor(), Ind=factor(), Mass=numeric(), Volume=numeric(), Init.O2=numeric(), Temp=numeric(), O2=numeric(), BOD=numeric(), O2.correct=numeric())

  for(i in 1:length(chlevels)){
    meas<-as.character(subset(slope.data, Chamber.No==chlevels[i])$Phase)
    chlevels.df<-subset(clean.data, Chamber.No==chlevels[i])
    for (m in 1:length(meas)){
      out.df<-subset(chlevels.df, Phase==meas[m])
      row.names(out.df)<-NULL
      extracted.data<-rbind(extracted.data, out.df)
    }
  }

  rm(chlevels)
  rm(i)
  rm(meas)
  rm(chlevels.df)
  rm(m)
  rm(out.df)

  if(residuals == F){
  a <-length(slope.data$Chamber.No[slope.data$Chamber.No == chamber])

  if (a <= 3){
    png(filename=paste0(TMPADRESS, "QC.slope_1.png"),width=1548,height=1160, pointsize = 30)
    par(mfrow = c(a, 1))

	  chamber.df<-subset(extracted.data, Chamber.No == chamber)
    meas<-unique(as.character(chamber.df$Phase))
    for(m in 1:length(meas)){
      m.df<-subset(chamber.df, Phase==meas[m])
      m1.df<-subset(m.df, Time<=current)
      m2.df<-subset(m.df, Time<=alter)
      model.1<-lm(O2.correct~Time, data=m1.df)
      model.2<-lm(O2.correct~Time, data=m2.df)
      plot(m.df$O2.correct~m.df$Time, main=meas[m], las=1, col = "steelblue2",
           xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""))

      abline(coef(model.1)[1], coef(model.1)[2], col="black", lwd=3)
      abline(coef(model.2)[1], coef(model.2)[2], col="red", lwd=3, lty=2)
      legend("topright", legend=c(expression(),
                                  bquote(.(current) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.1)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.1)[2], digits=5))),
                                  bquote(.(alter) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.2)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.2)[2], digits=5)))),
                                  lty=c(1,2), lwd=c(2,2), col=c("black", "red"))
      }
    dev.off()
  }

  else if(a > 3){
    chamber.df<-subset(extracted.data, Chamber.No == chamber)
    meas<-unique(as.character(chamber.df$Phase))
    A <- ceiling(a/3)
    for(i in 1:A){
      X <- 1 + (i-1)*3
      Y <- i*3
	  if(length(X:a) < 3){

      png(filename=paste(TMPADRESS, "QC.slope_", i, ".png", sep = ""),width=1548,height=1160, pointsize = 30)
      par(mfrow = c(3, 1))

      for(m in X:a){
        m.df<-subset(chamber.df, Phase==meas[m])
        m1.df<-subset(m.df, Time<=current)
        m2.df<-subset(m.df, Time<=alter)
        model.1<-lm(O2.correct~Time, data=m1.df)
        model.2<-lm(O2.correct~Time, data=m2.df)
        plot(m.df$O2.correct~m.df$Time, main=meas[m], las=1, col = "steelblue2",
        xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""))

        abline(coef(model.1)[1], coef(model.1)[2], col="black", lwd=3)
        abline(coef(model.2)[1], coef(model.2)[2], col="red", lwd=3, lty=2)
        legend("topright", legend=c(expression(),
                                    bquote(.(current) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.1)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.1)[2], digits=5))),
                                    bquote(.(alter) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.2)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.2)[2], digits=5)))),
                                    lty=c(1,2), lwd=c(2,2), col=c("black", "red"))
        }
      dev.off()
		}
	   else{

      png(filename=paste(TMPADRESS, "QC.slope_", i, ".png", sep = ""),width=1548,height=1160, pointsize = 30)
      par(mfrow = c(3, 1))

      for(m in X:Y){
        m.df<-subset(chamber.df, Phase==meas[m])
        m1.df<-subset(m.df, Time<=current)
        m2.df<-subset(m.df, Time<=alter)
        model.1<-lm(O2.correct~Time, data=m1.df)
        model.2<-lm(O2.correct~Time, data=m2.df)
        plot(m.df$O2.correct~m.df$Time, main=meas[m], las=1, col = "steelblue2",
        xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""))

        abline(coef(model.1)[1], coef(model.1)[2], col="black", lwd=3)
        abline(coef(model.2)[1], coef(model.2)[2], col="red", lwd=3, lty=2)
        legend("topright", legend=c(expression(),
                                    bquote(.(current) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.1)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.1)[2], digits=5))),
                                    bquote(.(alter) ~ "s:" ~ r^2 ~ "=" ~ .(round(summary(model.2)$r.sq, digits=3)) ~ "; slope" ~ "=" ~ .(round(coef(model.2)[2], digits=5)))),
                                    lty=c(1,2), lwd=c(2,2), col=c("black", "red"))
        }
        dev.off()
	      }
      }
    }
    b <- ceiling(a/3)
    print(b)
  }
  else{
    a <-length(slope.data$Chamber.No[slope.data$Chamber.No == chamber])
    chamber.df<-subset(extracted.data, Chamber.No == chamber)
    meas<-unique(as.character(chamber.df$Phase))

    for(m in 1:length(meas)){
      m.df<-subset(chamber.df, Phase==meas[m])
      m1.df<-subset(m.df, Time<=current)
      m2.df<-subset(m.df, Time<=alter)
      model.1<-lm(O2.correct~Time, data=m1.df)
      model.2<-lm(O2.correct~Time, data=m2.df)

        png(filename=paste0(TMPADRESS, "QC.slope_", m, ".png"),width=1548,height=1160, pointsize = 30)
        layout(matrix(c(1,1,1,2,3,4,5,6,7), 3, 3, byrow = TRUE))

        plot(m.df$O2.correct~m.df$Time, main=meas[m], las=1, col = "steelblue2",
             xlab = "Time (s)", ylab = paste("DO (", DOUNIT, "/L)", sep = ""))
        abline(coef(model.1)[1], coef(model.1)[2], col="black", lwd=3)
        abline(coef(model.2)[1], coef(model.2)[2], col = "red", lwd=3, lty=2)
        l.1<-paste(current, "s: r²=",(round(summary(model.1)$r.sq, digits=3)),"; slope= ",(round(coef(model.1)[2], digits=5)),sep="")
        l.2<-paste(alter, "s: r²=",(round(summary(model.2)$r.sq, digits=3)),"; slope= ",(round(coef(model.2)[2], digits=5)),sep="")
        legend("topright", legend=c(l.1,l.2), lty=c(1,2), lwd=c(3,3), col=c("black", "red"))

          panel.smooth.1 <- function (x, y, col = par("col"), bg = NA, pch = par("pch"),
            cex = 1, col.smooth = "black", span = 2/3, iter = 3, ...){
            points(x, y, pch = pch, col = col, bg = bg, cex = cex)
            ok <- is.finite(x) & is.finite(y)
            if (any(ok))
               lines(stats::lowess(x[ok], y[ok], f = span, iter = iter), col = col.smooth, ...)
            }

        plot(model.1, which = 1, col = "steelblue2", panel = panel.smooth.1, id.n = 0, lwd = 2)
        abline(h = 0, lty = 3, col = "black", lwd = 2)
        plot(model.1, which = 2, col = "steelblue2", panel = panel.smooth.1, id.n = 0, lwd = 2)
        qqline(rstandard(model.1), col = "black", lwd = 2, lty=3)
        mtext(paste("Regression Diagnostic for ", current, "s", sep = ""), side = 3, line = 2, font = 2, col.main= "blue")
        plot(model.1, which = 3, col = "steelblue2", panel = panel.smooth.1, id.n = 0, lwd = 2)
        plot(model.2, which = 1, col = "steelblue2", panel = panel.smooth, caption = NULL, col.lab="red", id.n = 0, lwd = 2)
        box(col = 'red')
        axis(1, col = 'red', col.axis = 'red', col.ticks = 'red')
        axis(2, col = 'red', col.axis = 'red', col.ticks = 'red')
        abline(h = 0, lty = 3, col = "red", lwd = 2)
        mtext("Residuals vs Fitted", side = 3, line = 0.3, col= "red")
        plot(model.2, which = 2, col = "steelblue2", panel = panel.smooth, caption = NULL, col.lab="red", id.n = 0, lwd = 2)
        qqline(rstandard(model.2), col = "red", lwd = 2, lty=3)
        box(col = 'red')
        axis(1, col = 'red', col.axis = 'red', col.ticks = 'red')
        axis(2, col = 'red', col.axis = 'red', col.ticks = 'red')
        mtext(paste("Regression Diagnostic for ", alter, "s", sep = ""), side = 3, line = 2, font = 2, col = "red")
        mtext("Normal Q-Q", side = 3, line = 0.3, col= "red")
        plot(model.2, which = 3, col = "steelblue2", panel = panel.smooth, caption = NULL, col.lab="red", id.n = 0, lwd = 2)
        mtext("Scale-Location", side = 3, line = 0.3, col= "red")
        box(col = 'red')
        axis(1, col = 'red', col.axis = 'red', col.ticks = 'red')
        axis(2, col = 'red', col.axis = 'red', col.ticks = 'red')

        rm(model.1)
        rm(model.2)
        rm(l.1)
        rm(l.2)
        rm(m.df)
        rm(m1.df)
        rm(m2.df)
        dev.off()
      }
      print(a)
    }
  }
