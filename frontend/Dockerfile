FROM node:alpine

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN npm run build

CMD ["node", "server.js"]