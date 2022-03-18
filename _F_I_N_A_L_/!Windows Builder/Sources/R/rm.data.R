rm.data <- function(clean.data,
                    chamber = c("CH1", "CH2", "CH3", "CH4",
                                 "CH5", "CH6", "CH7", "CH8"),
                    M.phase = "M0"){

  for(i in 1:length(M.phase)){
    clean.data$O2.correct[clean.data$Chamber.No == chamber & clean.data$Phase == M.phase[i]] <- 0
    }
  return(clean.data)
  }
