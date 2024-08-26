#!/bin/bash

# Passo 1: Parar o serviço Docker
echo "Parando o serviço Docker..."
sudo service docker stop

echo "Removendo configurações de rede antigas..."
sudo rm -rf /var/lib/docker/network

echo "Recriando diretório de sockets..."
sudo rm -rf /var/lib/docker/network/files
sudo mkdir -p /var/lib/docker/network/files

sudo service docker start

sudo service docker status


sudo reboot
