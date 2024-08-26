#!/bin/bash

echo "=== Resolvendo problemas com o Docker e iptables no WSL2 ==="

echo "Instalando iptables-legacy..."
sudo apt-get update
sudo apt-get install -y iptables-legacy

sudo update-alternatives --set iptables /usr/sbin/iptables-legacy
sudo update-alternatives --set ip6tables /usr/sbin/ip6tables-legacy

iptables --version

sudo service docker stop
sudo service docker start

sudo service docker status

echo "=== Script concluído. O Docker deve estar funcionando corretamente com iptables-legacy no WSL2 ==="
