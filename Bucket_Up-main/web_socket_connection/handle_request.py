import json , subprocess,re
patch_application = "/root/projeto_backup_puc/puc_cc_projeto_backup/web_socket_connection"
class receive_request():
    def receive(self,data):
        try:
            action = data['action']
        except:
            return {'status':'erro'}
        
        if (action == "generate"):
            #Tenta recuperar todos os campos necessarios
            try:
                data['ip']
                data['usuario']
                data['senha']
                data['pasta']
                data['nome_arquivo']
                data['porta']
            except:
                return "erro, parametros incorretos"
            print(data)
            nome_arquivo = data['nome_arquivo']+".tar.gz"
            nome_arquivo = re.sub(r':','_',nome_arquivo)
            execute = subprocess.Popen([patch_application+"/comunicacao.sh",data['ip'],data['usuario'],\
                data['senha'],data['pasta'],nome_arquivo,str(data['porta'])],stdout=subprocess.PIPE,stderr=subprocess.STDOUT)
            #Cruar vakudalçies aqyu casi becessario
        return {"status":"ok"}
