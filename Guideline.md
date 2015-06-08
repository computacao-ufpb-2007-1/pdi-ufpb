# Introduction #

Este é um comentário geral sobre a solução de processamento digital de imagens em Java.


# Details #

Toda imagem será encapsulada na classe `ImageWrapper` e representada por um array unidimensional de int, contendo os valores RGB de cada pixel.

O principal propósito da classe `ImageWrapper` é manter infomações sobre largura e altura da imagem, forçando a coerência desses valores e o comprimento do array que armazena a imagen. Secundariamente, a classe provê métodos para leitura e gravação de imagens, conversão de espaços de cores, adição de bordas e cortes na imagem.

Uma imagem `a` pode ser percorrida (linha por linha) com o seguinte loop:

```
int data[] = a.image;
int row;
for (int i = 0; i < a.heigth; ++i) {
    row = i*a.width;
    for (int j = 0; j < a.width; ++j) {
        //seu codigo aqui. Pixel atual pode ser acessado assim:
        // data[row+j];
    }
}
```


A qualquer momento, para cada instância de `ImageWrapper`, a imagem válida é a que está no array referenciado por `image`.

Contudo, seis componentes estão disponíveis (R, G, B, Y, U, V):

Eles podem ser calculados a partir dos métodos adequados.

As componentes podem não conter dados, em qualquer momento da execução.


Cada componente é representada pela classe `ColorComponent`, por um array de float. Esta representaçao diminui erros de arredondamento sucessivos, pois apenas dois arredondamentos são necessários: um na conversão para float, e uma na conversão de volta para int. Isto quer dizer que você pode trabalhar com float o tempo todo e apenas perder um pouco de precisão ao retornar para a imagem original.

Neste array, 0.0f representa o valor mínimo da cor. Qualquer valor abaixo do mínimo representa o mínimo; 1.0f representa o valor máximo da cor. Qualquer valor acima do máximo representa o máximo.


**Nota importante:**
  * Os filtros devem ser implementados para agir em cima de Instancias da classe `ColorComponent`.