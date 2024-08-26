#!/bin/bash

set -e

echo "============================================="
echo "Iniciando a desinstalação completa e forçada do Docker..."
echo "============================================="

# Função para matar processos
kill_processes() {
    echo "Matando todos os processos Docker..."
    # Lista de possíveis processos Docker
    local processes=("docker" "docker-containerd" "docker-containerd-shim" "containerd")

    for process in "${processes[@]}"; do
        if pgrep -x "$process" > /dev/null; then
            echo "Matando processo: $process"
            sudo pkill -x "$process" || true
            sudo killall -9 "$process" || true
        fi
    done
}

# 1. Parar os serviços Docker
echo "Parando os serviços Docker..."
sudo systemctl stop docker || true
sudo systemctl stop containerd || true

# 2. Matar processos Docker
kill_processes

# 3. Remover arquivos de PID
echo "Removendo arquivos PID..."
sudo rm -f /var/run/docker.pid
sudo rm -f /var/run/docker/containerd/docker-containerd.pid
sudo rm -f /var/run/docker/containerd/docker-containerd-shim.pid
sudo rm -f /var/run/docker/containerd/docker-containerd-shim/* || true

# 4. Remover arquivos de serviço e sockets
echo "Removendo arquivos de serviço e sockets..."
sudo rm -f /etc/systemd/system/docker.service
sudo rm -f /etc/systemd/system/docker.socket
sudo rm -f /etc/systemd/system/multi-user.target.wants/docker.service
sudo rm -f /etc/systemd/system/sockets.target.wants/docker.socket
sudo rm -f /usr/lib/systemd/system/docker.service
sudo rm -f /usr/lib/systemd/system/docker.socket

# 5. Remover diretórios Docker
echo "Removendo diretórios do Docker..."
sudo rm -rf /var/lib/docker
sudo rm -rf /var/lib/containerd
sudo rm -rf /etc/docker
sudo rm -rf /run/docker
sudo rm -rf /var/run/docker.sock

# 6. Remover grupo Docker
echo "Removendo grupo Docker..."
sudo groupdel docker || true

# 7. Remover repositórios e chaves APT do Docker
echo "Removendo repositórios e chaves APT do Docker..."
sudo rm -f /etc/apt/sources.list.d/docker.list
sudo rm -f /etc/apt/keyrings/docker.asc

# 8. Substituir o script de pré-desinstalação (prerm) com um script vazio
PRERM_FILE="/var/lib/dpkg/info/docker-ce.prerm"
if [ -f "$PRERM_FILE" ]; then
    echo "Substituindo o script de pré-removal docker-ce.prerm com um script vazio..."
    sudo sh -c 'echo -e "#!/bin/sh\nexit 0" > /var/lib/dpkg/info/docker-ce.prerm'
    sudo chmod +x /var/lib/dpkg/info/docker-ce.prerm
fi

# 9. Remover arquivos de configuração dpkg relacionados ao Docker
echo "Removendo arquivos de configuração dpkg relacionados ao Docker..."
sudo rm -f /var/lib/dpkg/info/docker-ce.*
sudo rm -f /var/lib/dpkg/info/docker-ce-rootless-extras.*
sudo rm -f /var/lib/dpkg/info/docker-ce-cli.*
sudo rm -f /var/lib/dpkg/info/containerd.io.*
sudo rm -f /var/lib/dpkg/info/docker-buildx-plugin.*
sudo rm -f /var/lib/dpkg/info/docker-compose-plugin.*

# 10. Forçar a remoção do pacote docker-ce
echo "Forçando a remoção do pacote docker-ce..."
sudo dpkg --remove --force-remove-reinstreq docker-ce || true

# 11. Remover quaisquer pacotes restantes (docker-ce)
echo "Purge docker-ce se ainda estiver instalado..."
sudo apt-get purge -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin docker-ce-rootless-extras || true

# 12. Limpar pacotes órfãos e cache
echo "Limpando pacotes órfãos e cache..."
sudo apt-get autoremove -y
sudo apt-get autoclean
sudo apt-get clean

# 13. Remover diretórios de dados Docker restantes
echo "Removendo diretórios de dados Docker restantes..."
sudo rm -rf /var/lib/docker
sudo rm -rf /var/lib/containerd
sudo rm -rf /etc/docker
sudo rm -rf /run/docker
sudo rm -rf /var/run/docker.sock

# 14. Atualizar a lista de pacotes
echo "Atualizando a lista de pacotes..."
sudo apt-get update

echo "============================================="
echo "Desinstalação completa e forçada do Docker concluída."
echo "============================================="

# 15. Reiniciar o sistema (opcional)
read -p "Deseja reiniciar o sistema agora? (s/n): " answer
if [[ "$answer" =~ ^[Ss]$ ]]; then
    echo "Reiniciando o sistema..."
    sudo reboot
else
    echo "Reinicialização do sistema cancelada. Por favor, reinicie manualmente se necessário."
fi
