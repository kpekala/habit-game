cd backend
./gradlew build
cd ../frontend
npm run build
cd ../
java -jar backend/build/libs/habitgame-0.0.1-SNAPSHOT.jar &
node frontend/server.js &
