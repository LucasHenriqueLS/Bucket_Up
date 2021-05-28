#!/bin/bash -x

#Esse script vai acessar o equipamento, compactar as pastas necessarias, baixar o arquivo compactado e enviar ele pra uma pasta
#Que será acessivel externamente

#Recebe as variaveis de conexão
ip=$1
usuario=$2
senha=$3

#Recebe as pastas a serem processadas
#Formato:
#pasta1,pasta2,pasta3,pasta4
pasta_in=$4
pasta=`echo "$pasta_in" | tr  ',' ' '`
nome_arquivo=$5
porta=$6

sshpass -p"$senha"  ssh -o StrictHostKeyChecking=no -p $porta $usuario@$ip "tar -zcvf $nome_arquivo $pasta"

#Extrai o arquivo, e envia para a pasta compartilhada
#Na aplicação, a pasta compartilhava vai ser a /var/www/projeto_backup/arquivos
sshpass -p"$senha" scp -o StrictHostKeyChecking=no -p $porta $usuario@$ip:$nome_arquivo /var/www/projeto_backup/arquivos

#Remove o arquivo criado dentro do equipamento
sshpass -p"$senha"  ssh -o StrictHostKeyChecking=no -p $porta $usuario@$ip "rm $nome_arquivo"

