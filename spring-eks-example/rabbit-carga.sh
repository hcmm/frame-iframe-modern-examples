#!/bin/bash

# Número total de requisições e duração do teste (5 minutos)
total_requests=1500
duration=300  # 5 minutos em segundos
requests_per_second=$((total_requests / duration))

for ((i=1; i<=$total_requests; i++)); do
  # Envia a requisição
  ab.exe -n 1 -c 1 http://localhost:8086/spring-eks-example/api/send?message=retry >/dev/null 2>&1
  
  # Aguarda até o próximo segundo para espaçar as requisições
  sleep $(echo "scale=2; 1/$requests_per_second" | bc)
done
