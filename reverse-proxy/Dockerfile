FROM nginx:stable-alpine

# Copier la configuration nginx
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

# Healthcheck basique
HEALTHCHECK --interval=5s --timeout=3s --retries=10 CMD wget -qO- http://localhost/ || exit 1 