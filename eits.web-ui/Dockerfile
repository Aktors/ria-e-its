FROM node:18 as build
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build --dev


FROM nginx:alpine
COPY --from=build /app/dist/eits.web-ui/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
