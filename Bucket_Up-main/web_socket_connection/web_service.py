#_*_ coding : utf-8 _*_
import json,os,sys,re
from flask import Flask , request 
from flask_restful import Resource, Api
from flask_cors import CORS
import func_principal as fp
import handle_request as hr
'''
Pacotes necessarios
apt-get install pip3
pip install --upgrade pip
pip3 install flask
pip3 install flask_restful
pip3 install flask_cors
'''

class main():
    def __init__(self):
        start_flask()

class handle_request():
    def request_in(method):
        
        try:
            #Tenta receber pelo body
            data_in = request.get_json(force=True)
        except:
            #Caso nao consiga, recebe pela url
            data_in = request.args
            tmp = {}
            for i in  data_in:
                tmp.update({i:data_in[i]})
            data_in = tmp
        try:
            r = hr.receive_request()
            out = r.receive(data_in)
            return out
        except Exception as e:
            return str(e)
class start_flask():
    def __init__(self):
        app = Flask(__name__)
        api = Api(app)
        CORS(app)
        api.add_resource(execute_request,'/')
        try:
            app.run(debug=True,host="0.0.0.0",port="8765")
        except:
            print("start error")
            exit()

class execute_request(Resource):
    def get(self):
        return handle_request.request_in('get')
    def post(self):
        return handle_request.request_in('post')
if __name__ == "__main__":
    main()