# bin/bash
docker stop mail-service || true
docker rm mail-service || true
docker rmi leesg107/mail-service || true
./gradlew clean bootBuildImage --imageName=leesg107/mail-service
