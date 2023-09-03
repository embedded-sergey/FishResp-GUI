#/bin/bash
sleep 5
bash -c "export R_HOME=\"/usr/lib/R\" && cd '/usr/lib/FishResp' && java -Djdk.gtk.version=2 -Xss2560k -jar FishRespBase.jar" </dev/null &>/dev/null &
