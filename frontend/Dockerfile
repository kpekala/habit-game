FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm install

RUN npm run build

RUN chmod u+r+x  dist/frontend/index.html

CMD ["node", "server.js"]