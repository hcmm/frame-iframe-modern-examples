#!/bin/bash

echo "Iniciando a desinstalação completa do Docker..."

# Parar o serviço Docker
echo "Parando o serviço Docker..."
sudo systemctl stop docker
sudo systemctl stop containerd

sudo service docker stop
sudo service containerd stop

# Matar processos relacionados ao Docker
echo "Matando processos Docker remanescentes..."
sudo killall -9 docker-containerd-shim
sudo killall -9 docker

# Forçar a remoção de pacotes Docker
echo "Removendo pacotes Docker..."
sudo dpkg --force-all --purge docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

# Remover repositórios e chaves APT do Docker
echo "Removendo repositórios e chaves APT do Docker..."
sudo rm /etc/apt/sources.list.d/docker.list
sudo rm /etc/apt/keyrings/docker.asc

# Remover diretórios de dados do Docker
echo "Removendo diretórios de dados do Docker..."
sudo rm -rf /var/lib/docker
sudo rm -rf /var/lib/containerd
sudo rm -rf /etc/docker
sudo rm -rf /run/docker
sudo rm -rf /var/run/docker.sock

# Remover arquivos de configuração do Docker
echo "Removendo arquivos de configuração do Docker..."
sudo rm -rf /etc/systemd/system/docker.service
sudo rm -rf /etc/systemd/system/docker.socket
sudo rm -rf /etc/systemd/system/multi-user.target.wants/docker.service
sudo rm -rf /etc/systemd/system/sockets.target.wants/docker.socket
sudo rm -rf /usr/lib/systemd/system/docker.service
sudo rm -rf /usr/lib/systemd/system/docker.socket

# Remover imagens, containers e volumes residuais
echo "Removendo imagens, containers e volumes residuais..."
sudo rm -rf /var/lib/docker/images
sudo rm -rf /var/lib/docker/containers
sudo rm -rf /var/lib/docker/volumes

# Remover grupo de usuários Docker
echo "Removendo grupo de usuários Docker..."
sudo groupdel docker

# Limpar pacotes órfãos e dependências
echo "Limpando pacotes órfãos e dependências..."
sudo apt-get autoremove -y
sudo apt-get autoclean
sudo apt-get clean

echo "Desinstalação completa do Docker concluída."

# Reiniciar o sistema (opcional)
read -p "Deseja reiniciar o sistema agora? (s/n): " answer
if [ "$answer" != "${answer#[Ss]}" ]; then
    echo "Reiniciando o sistema..."
    sudo reboot
else
    echo "Reinicialização do sistema cancelada. Por favor, reinicie manualmente se necessário."
fi
