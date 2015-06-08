# Introduction #

```
def func1(): # I'm an endline comment

    'Boo, identation matters!' # this is a function documentation string

    print "I'm func1!"
    def func2():
        print "Im func2, and I live inside func1 o.O"
    func2()

func1() #just invoking something called func1

# there's no need of semi-colons, yupi!

```


# Details #

Ok, nosso projeto funciona em Windows, apenas.

Você vai precisar de:

> - Webcam compatível com Windows;

> - Python 2.5.4 => www.python.org ;

> - Biblioteca OpenCV (C (omg, omg!)) (dlls estao no svn, so, don't care about this line). ;

> - Biblioteca Ctypes OpenCV, versão 0.8.0 (python bindings for OpenCV). http://ctypes-opencv.googlecode.com/files/ctypes-opencv-0.8.0.win32-py25.exe

> - Biblioteca Python Imaging Library (PIL). http://effbot.org/downloads/PIL-1.1.6.win32-py2.5.exe

> - Biblioteca PyGame. http://pygame.org/ftp/pygame-1.9.1release.win32-py2.5.exe

> - Biblioteca NumPy. http://sourceforge.net/projects/numpy/files/NumPy/1.3.0/numpy-1.3.0-win32-superpack-python2.5.exe/download


Podemos usar o IDLE (IDE que acompanha o python), junto com o tortoiseSVN, para acesso ao repositório,

OU

O NetBeans 6.5, com plugin para Python instalado.