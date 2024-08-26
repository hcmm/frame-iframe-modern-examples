#!/bin/bash

echo "=== Resolvendo 'Error creating default bridge network: permission denied' ==="

# Passo 1: Parar o serviço Docker
echo "Parando o serviço Docker..."
sudo service docker stop

# Passo 2: Remover configurações de rede antigas
echo "Removendo configurações de rede antigas..."
sudo rm -rf /var/lib/docker/network

# Passo 3: Recriar diretório de sockets
echo "Recriando diretório de sockets..."
sudo rm -rf /var/lib/docker/network/files
sudo mkdir -p /var/lib/docker/network/files

# Passo 4: Reiniciar o serviço Docker
echo "Reiniciando o serviço Docker..."
sudo service docker start

# Passo 5: Verificar se o Docker está funcionando corretamente
echo "Verificando o status do Docker..."
sudo service docker status

echo "=== Script concluído. Reiniciando o sistema... ==="

# Passo 6: Reiniciar o sistema
sudo reboot
