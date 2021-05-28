//Funções relacionadas a tela de login e usuario
function alpha(e){
    var k;
    document.all ? k = e.keyCode : k = e.which;
    return (k >= 65 && k <= 90 || k >= 97 && k <= 122 || k == 8 || k == 32 || k >= 48 && k <= 57);
}
function do_login(){
    d = get_data_from_from("#form_login")
    patch = "operate_use/check_login"
    make_request(d,do_login_callback,patch)
    console.log(d)
}
function do_login_callback(data){
    if(data.status){
        document.cookie = "id_usuario="+data.id+""
        console.log(document.cookie)
        redirect_after_login()
    }else{
        change_element("id","login_error","","attr",["hidden",false])
    }
}
function create_user(){
    d = get_data_from_from("#form_create_user")
    patch = "operate_user/check_exist"
    make_request(d,check_usuario_exist_callback,patch)
    console.log(d)
}
function create_user_callback(data){
    if(data.status){
        redirect_after_login()
    }else{
        change_element("id","login_error","","attr",["hidden",false])
    }
}
function update_user(){
    d = get_data_from_from("#form_update_usuario")
    patch = "operate_user/check_exist"
    make_request(d,check_usuario_exist_update_callback,patch)
    console.log(d)
}
function update_callback(data){
    if(data.status){
        redirect_after_login()
    }else{
        change_element("id","login_error","","attr",["hidden",false])
    }
}
function check_usuario_exist_callback(data){
    if(data.status){
        change_element("id","usuario_exist_error","","attr",["hidden",false])
    }else{
        d = get_data_from_from("#form_create_user")
        patch = "operate_user/create_user"
        make_request(d,create_user_callback,patch)
    }
}
function check_usuario_exist_update_callback(data){
    console.log(data)
    if(data.status){
        d = get_data_from_from("#form_update_usuario")
        patch = "operate_user/update_usuario"
        make_request(d,update_callback,patch)
        
    }else{
        change_element("id","usuario_exist_error","","attr",["hidden",false])
    }
}
function redirect_after_login(){
    window.location.href = 'http://127.0.0.1:5500/equipamento.html';
}
//Funções relacionadas a tela de login e usuario
//-------------------------------------------------------------------------------------------------------------------------------------------
//Funções relacionadas a operação do equipamento
function listar_equipamento(){
    
    d = {"id_user":get_id_usuario_from_cookie()}
    patch = "operate_equipment/listar_equipment"
    make_request(d,listar_equipamento_callback,patch)

}
function listar_equipamento_callback(data){
    h = '\
    <tr>\
        <th>id</th>\
        <th>Hostname</th>\
        <th>IP</th>\
        <th>Port</th>\
        <th>Usuario</th>\
        <th>Senha</th>\
        <th>Pastas</th>\
        <th>Agendamento</th>\
        <th>Ações</th>\
    </tr>\
    '
    c = [
        { data: 'id' },
        { data: 'hostname' },
        { data: 'ip' },
        { data: 'port' },
        { data: 'usuario_login' },
        { data: 'senha_login' },
        { data: 'pastas' },
        { data: 'agendamento' },
        { data: 'action' }
        
    ]

    //Altera o vetor recebido, adicionando os campos de ação de update e delete, cada um com seu respectivo botão
    vet_out = []
    $.each(data,function(k,v){
        tmp = v
        ac_d = JSON.stringify(tmp)
        ac = '\
        <div style="text-align: center;">\
        <button  title="Editar" class="badge btn btn-outline-teal btn-ico" type="button" onclick=\'upload_update('+ac_d+')\'><i data-feather="layers" color="black"></i></button>\
        <button  title="Remover" class="badge btn btn-outline-red btn-ico" type="button"  onclick=\'handle_request_dell('+ac_d+')\'><i data-feather="trash" color="black"></i></button>\
        <button  title="Executar" class="badge btn btn-outline-blue btn-ico" type="button"  onclick=\'handle_execution('+ac_d+')\'><i data-feather="airplay" color="black"></i></button>\
        </div>\
        '
        tmp['action'] = ac
        vet_out.push(tmp)
    })

    console.log(data)
    create_table_array("tabela_equipamento",h,vet_out,c)
}
function handle_request_dell(id_dell){
    d={"id":id_dell.id}
    patch = "operate_equipment/delete_equipment"
    make_request(d,dell_callback,patch)
}
function dell_callback(data){
    clean_table("tabela_equipamento")
    listar_equipamento()
}

function handle_request(){
    d = get_data_from_from("#form_equipamento")
    d['id_user'] = id_usuario
    d['id'] = id_equipamento
    d['usuario_login'] = d.usuario
    d['senha_login'] = d.senha
    op = $("#operation").val()
    if (op == "save"){
        patch = "operate_equipment/create_equipment"
    }else{
        patch = "operate_equipment/update_equipment"
    }
    
    make_request(d,handle_request_callback,patch)
}
function handle_request_callback(data){
    console.log(data)
    change_save_button("save")
    clean_table()
    clean_form()
    listar_equipamento()
}
//Funções relacionadas a operação do equipamento
//-------------------------------------------------------------------------------------------------------------------------------------------

//Muda o elemento com base na key informada
function change_element(tag,key,value="",action="text",attr_change=false){
    element = $("["+tag+"="+key+"]")
    if (action == "text"){
        element.text(value);
    }else if (action == "attr"){
        element.attr(attr_change[0],attr_change[1])
    }
}

//Essa função faz a aquisição de todos os dados do formulario selecionado
//O parametro é o cmapo completo a ser selecionado
//Exemplo #id para o id [name=nome] para buscar pelo nome e etc
function get_data_from_from(form_reference){
    var t = {}
    $(form_reference).find('input,select').each(function(){
        key = $(this).attr('id')
        value = $(this).val()
        t[key] = value
    })
    
return t
}

//Função responsavel por realizar requisiçoes HTTP
function make_request(body,callback,patch){
    out = ''
    url_request = "http://127.0.0.1:8081/"+patch
    $.ajax({
        url:url_request,
        method:'POST',
        data:body
    }).done(function(data){
       out = data
       callback(out)
    })
    
}
function clean_form(){
    $('#form_equipamento').each (function(){
        this.reset();
      });
      change_save_button("save")
}
function change_save_button(mode){
    if (mode =="save"){
        $("#operation").val("save")
        $("#operation").text("Salvar")
        change_element("id","operation","","attr",["class","btn btn-primary"])
    }else{
        $("#operation").val("update")
        $("#operation").text("Atualizar")
        change_element("id","operation","","attr",["class","btn btn-warning"])
    }
}
function handle_password(id=false,btn_id=false){
    if(id == false && btn_id == false){
        id = "password"
        btn_id = "btn_pass"
    }
    t = $("#"+id).attr("type")
    if (t=="password"){
        $("#"+btn_id).text("")
        $("#"+btn_id).append('<i  data-feather="eye-off"></i>')
        $("#"+id).attr("type","text")
        
    }else{
        $("#"+btn_id).text("")
        $("#"+btn_id).append('<i  data-feather="eye"></i>')
        $("#"+id).attr("type","password")
    }
    feather.replace()
}

function create_table_array(tb_id,head,data_array,colums,id_div=false,colums_def=[],pg_length=10){
    tabela = $("#"+tb_id)
    tabela_cabecalho = $("#"+tb_id+" thead")
    head_html = head
    util_data = data_array
    tabela_cabecalho.html(head_html)
    //Destrou datable antes de criar
    tabela.DataTable().destroy()
    

    tabela.DataTable({
        data:util_data,
        columns: colums,
        searching : true,
        renderer: {
            "pageButton": "bootstrap"
        },
        "language": {
            "search": "Pesquisar:",
            "sLengthMenu":"Exibir _MENU_ registros",
            "sInfo":"Exibindo _START_ até _END_ de _TOTAL_ entradas",
            "sPrevius":"Anterior",
            "paginate": {
                "previous": "Anterior",
                "next":"Proximo"
              }
          },
          "pageLength": pg_length,
          "aaSorting": [],
          "order": [],
          "drawCallback": function( settings ) {
            feather.replace()
        },"columnDefs": colums_def
    })
    if (id_div !=false){
        change_element("id",id_div,"","attr",["hidden",false])
    }
}
function clean_table(id){
    $("#"+id).empty()
    $("#"+id+" thread").empty()
    $("#"+id).empty()
    $("#"+id).DataTable().destroy()
    $("tr").each(function() {
        $(this).children("th").remove();
    });
}

//Função para atualizar o formulario com as informações da tabela
function upload_update(data){
    id_equipamento = data['id']
    delete data['id']
    console.log(data)
    $("#hostname").val(data.hostname)
    $("#port").val(data.port)
    $("#ip").val(data.ip)
    $("#senha").val(data.senha_login)
    $("#usuario").val(data.usuario_login)
    $("#pastas").val(data.pastas)
    $("#agendamento").val(data.agendamento)
    change_save_button("update")
}

//Função para lidar com os selects do sistema
function generate_select(id_select,source_data,key,value,multiple=false){
    $.each(source_data,function(k,v){
        if(multiple == true){
            tmp_value = {}
            $.each(key,function(i,key_now){
                d = v[key_now]
                tmp_value[key_now] = d
            })
            value_insert = JSON.stringify(tmp_value)
            
        }else{
            value_insert = v[key]
        }
        console.log(v[value]+" "+value_insert)
        console.log($(id_select))
        $(id_select).append('<option class="dropdown-item" value=\''+value_insert+'\'>'+v[value]+'</option>')
    })
    
}
//Funções relacionadas a pagina inicial
//-------------------------------------------------------------------------------
function get_all_equipments(){
    d = {"id_user":get_id_usuario_from_cookie()}
    console.log(d)
    patch = "operate_equipment/listar_equipment"
    make_request(d,get_all_equipments_callback,patch)
}
function get_all_equipments_callback(data){
    generate_select("#equipamento_filtro",data,["id","hostname"],"hostname",true)
    listar_equipamento()
    wait_change_equipamento_filtro()
    
}
//Funções relacionadas a pagina inicial
//-------------------------------------------------------------------------------
document.getElementById("ip").onkeypress = function(e){
    var chr = String.fromCharCode(e.which);
    if("1234567890.".indexOf(chr) < 0)
        return false;
}
document.getElementById("agendamento").onkeypress = function(e){
    var chr = String.fromCharCode(e.which);
    if("1234567890:".indexOf(chr) < 0)
        return false;
}
function get_id_usuario_from_cookie(){
    c = document.cookie
    id_usuario = c.split("=")[1].split(";")[0]
    return id_usuario
}
function handle_execution(data){
    d = data
    d["action"] = "generate"
    patch = "operate_arquivo/create_arquivo"
    make_request(d,handle_execution_callback,patch)
}
function handle_execution_callback(data){
    handle_list_log()
}
function wait_change_equipamento_filtro(){
    equipamento_filtro = $("#equipamento_filtro")
    equipamento_filtro.change(function(){
        t = $("#tabela_equipamento").DataTable()
        eq_val = JSON.parse(equipamento_filtro.val()).hostname
        if(eq_val == ''){
            console.log("jovem")
            t.column(1)
            .search('').draw()
        }else{
            t.column(1)
            .search("^" + eq_val + "$", true, false, true)
            .draw();
        }
        handle_list_log()

    })
}

//Função relacionada a leitura do log
function handle_list_log(){
    equipamento_filtro = $("#equipamento_filtro")
    qe_val = JSON.parse(equipamento_filtro.val()).id
    d = {"id_equipment":qe_val}
    patch = "operate_log/listar_log"
    make_request(d,handle_list_log_callback,patch)
}
function handle_list_log_callback(data){
    h = '\
    <tr>\
        <th>Nome do arquivo</th>\
        <th>Hora de execução</th>\
        <th>Download</th>\
    </tr>\
    '
    c = [
        { data: 'nome_arquivo' },
        { data: 'hora_execucao' },
        { data: 'action' }        
    ]
    vet_out = []
    $.each(data,function(k,v){
        tmp = v
        ac_d = JSON.stringify(tmp)
        ac = '\
        <div style="text-align: center;">\
        <button  title="Download" class="badge btn btn-outline-blue btn-ico" type="button"  onclick=\'handle_download('+ac_d+')\'><i data-feather="download-cloud" color="black"></i></button>\
        </div>\
        '
        tmp['action'] = ac
        vet_out.push(tmp)
    })
    create_table_array("tabela_evento",h,vet_out,c,5)
}
function handle_download(data){
    source_files_link_base = "http://192.168.80.130:7071/"
    file = data.nome_arquivo
    file = file.replaceAll(":","_")
    file = file+".tar.gz"
    link = source_files_link_base+file
    window.open(link, 'name'); 
}
id_equipamento = -1
