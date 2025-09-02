# Builder
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# Dev/runtime: use Vite dev server in dev compose (this image is reused for build in prod compose)
FROM node:20-alpine AS dev
WORKDIR /app
COPY --from=builder /app /app
EXPOSE 5173
CMD ["npm", "run", "dev", "--", "--host"] 