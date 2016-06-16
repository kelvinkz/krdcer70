#include <Keypad.h>
#include <LiquidCrystal.h>
#include <EEPROM.h>

// Configuracoes do teclado
const byte Coluna = 3;
const byte Linha = 4;
char Teclas[Linha][Coluna] = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', '9'}, {'*', '0', '#'}};
byte Pino_linha[Linha] = {4, 5, 6, 7};
byte Pino_coluna[Coluna] = {8, 9, 10};
Keypad keypad = Keypad(makeKeymap(Teclas), Pino_linha, Pino_coluna, Linha, Coluna );

// Configuracoes do LCD
LiquidCrystal lcd(12, 11, 3, 2, 1, 0);

// Configuracoes gerais
const String senhaFabrica = "123456"; // Senha de fabrica
String senhaPadrao = ""; // Senha do usuario pre configurada
String senha = ""; // Senha digitada pelo usuario
int erro = 0; // Variaveis global para contar as tentativas falhas de autenticacao

enum Estado { // Possiveis estados da tranca
  ABERTO,
  FECHADO,
  BLOQUEADO_FABRICA,
  TROCANDO_SENHA
};

// Inicia em estado FECHADO
Estado estado = FECHADO;

void setup() {

  // Pino Buzzer como saida
  pinMode(13, OUTPUT);

  // Pino do LED RGB como saida
  pinMode(A0, OUTPUT);
  pinMode(A1, OUTPUT);
  pinMode(A2, OUTPUT);

  // Iniciando LCD
  lcd.begin(16, 2);

  // Carregando senha padrao salva
  senhaPadrao.concat(String(EEPROM.read(0)));
  senhaPadrao.concat(String(EEPROM.read(1)));
}

void loop() {
  initLCD();
  acendeLed();  
  digitarSenha();
}

void salvarSenha() {
  int left = senhaPadrao.substring(0, 2).toInt();
  int right = senhaPadrao.substring(2, 4).toInt();
  EEPROM.write(0, left);
  EEPROM.write(1, right);
}

void initLCD() {
  lcd.setCursor(0, 0);
  if (estado == BLOQUEADO_FABRICA) {
    lcd.print("Senha Fabrica:");  
  } 
  if (estado == FECHADO) {
    lcd.print("Digite a senha:");
  }
  if (estado == TROCANDO_SENHA) {
    lcd.print("Nova senha:");
  }
}

void digitarSenha() {
  char teclaPressionada = keypad.getKey();
  if (teclaPressionada != NO_KEY) {
    switch(teclaPressionada){
      case '#':
        alterarSenha();
      break;
      
      case '*':
        if (estado == TROCANDO_SENHA) {
          alterarSenha();  
        } 
        if (estado == BLOQUEADO_FABRICA) {
          validaSenhaFabrica();
        } 
        if (estado == FECHADO){
          validaSenha();
        }
        if (estado == ABERTO) {
          estado = FECHADO;
        }
      break;
      
      default:
        senha.concat(teclaPressionada);
        lcd.clear();
        for (int i = 0; i < senha.length(); i++) {
          lcd.setCursor(i, 1);
          lcd.print('*'); 
        }
      break;
    }
  }
}

void alterarSenha() {
  switch (estado) {
    case TROCANDO_SENHA:
    if (senha.length() == 4){
        senhaPadrao = senha;
        estado = FECHADO;
        lcd.clear();
        salvarSenha();
    } else {
      lcd.clear();
      lcd.print("SENHA 4 DIGITOS!");
      delay(1000);
      lcd.clear();
      senha = "";
    }
    break;
    case FECHADO:       
    if (senha.length() == 6){
      if (senha.equals(senhaFabrica)){
        estado = TROCANDO_SENHA;
        senha = "";
        lcd.clear();
        initLCD();
      }
    }
    break;
  }
}

void validaSenha(){
  if (senha.length() == 4){
    if (senha == senhaPadrao){
      abrir();
    } else {
      errou();
    }
  } else {
    errou();
  }
}

void validaSenhaFabrica() {
  if (senha.length() == 6){
    if (senha == senhaFabrica){
      estado = FECHADO;
      alterarSenha();
    } else {
      errou();
    }
  } else {
    errou();
  }  
}

void abrir() { // Acao executada ao acertar uma tentativa de autenticacao
  estado = ABERTO;
  acendeLed();
  lcd.clear();
  lcd.print("Open!");
  playBuzzer();
  delay(5000);
  senha = "";
  erro = 0;
}

void errou() { // Acao executada ao errar uma tentativa de autenticacao
  lcd.clear();
  lcd.print("SENHA INCORRETA!");
  playBuzzer();
  delay(1000);
  lcd.clear();
  senha = "";
  erro++;
  if (erro >= 3) {
    estado = BLOQUEADO_FABRICA;
  }
}

void playBuzzer() { // Toca uma vez o buzzer com delay de 1 segundo
  digitalWrite(13, HIGH);
  delay(1000);
  digitalWrite(13, LOW);
}

void acendeLed() { // Atualiza o LED de acordo com o estado atual
  switch(estado){
    case ABERTO:
    analogWrite(A0, 0);
    analogWrite(A1, 255);
    analogWrite(A2, 0);
    break;
      
    case BLOQUEADO_FABRICA:
    analogWrite(A0, 0);
    analogWrite(A1, 0);
    analogWrite(A2, 255);
    break;
      
    case FECHADO:
    analogWrite(A0, 255);
    analogWrite(A1, 0);
    analogWrite(A2, 0);
    break;
  }
}
