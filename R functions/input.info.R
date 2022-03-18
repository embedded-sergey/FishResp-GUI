input.info <- function(ID = c(NA, NA, NA, NA, NA, NA, NA, NA),
                 Mass = c(NA, NA, NA, NA, NA, NA, NA, NA),
                 Volume = c(NA, NA, NA, NA, NA, NA, NA, NA)){

  info.data <- as.data.frame(cbind(ID, Mass, Volume))
  info.data$Mass <- as.numeric(as.character(info.data$Mass))
  info.data$Volume <- as.numeric(as.character(info.data$Volume))

  print(info.data)
  return(info.data)
}
