cd backend
./gradlew build
cd ../frontend
npm run build
cd ../
docker build -t habitgame/backend ./backend
docker build -t habitgame/frontend ./frontend