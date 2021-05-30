function getBotResponse(input) {
    // respostas
    // saudações
    if (input == "oi" || input == "ola" || input == "opa" || input == "eae") {
        return "Olá! Em que posso ajudar?";
    } 
    // perguntas sobre a plataforma
    else if (input == "pra que serve esse site?" || input == "qual o intuito desse site?" || input == "esse site faz o que?") {
        return "O propósito desse site é facilitar o processo de backup para pequenas e grandes empresas, já que é um processo repetitivo que geralmente necessita uma pessoa específica apenas para realiza-lo.";
    } else if (input == "segurança" || input == "é seguro?" || input == "corro risco?") {
        return "Nosso site é seguro e você não corre o risco de perder ou expor seus dados para indivíduos indesejáveis.";
    } else if (input == "erro" || input == "em caso de erro" || input == "problema") {
        return "Entre em contato com nossa equipe em caso de erro na plataforma.";
    } else if (input == "esqueci minha senha" || input == "perdi minha senha" || input == "perdi meu acesso") {
        return "Você pode mudar a sua senha apertando na opção Esqueci a Senha.";
    } else if (input == "quem criou?" || input == "quem fez?" || input == "quem criou o site?" || input == "quem fez o site?") {
        return "Nosso grupo é composto por Bárbara Pereira, Christian Silva, Lucas Loureiro, Pedro Miranda e Thais Lara.";
    }
    // despedidas
    else if (input == "tchau" || input == "obrigada" || input == "obrigado" || input == "obg" || input == "obgd" || input == "valeu") {
        return "Tchauzinho! Espero ter ajudado.";
    } 
    // sem pergunta coerente
    else {
        return "Não consegui identificar sua pergunta. Tente novamente.";
    }
}