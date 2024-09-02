cd backend
./gradlew build
cd ../
docker build -t kpekala/habitgame-backend ./backend
docker build -t kpekala/habitgame-frontend ./frontend