from tkinter import *
from tkinter.filedialog import askopenfilename
from C45 import *
from ID3 import *


class App(object):
    
    def __init__(self, master, info = 'Train Decision Tree'):
        self.master = master
        Label(master, text="决策树模型").grid(row=0, column=2)
        Label(master, text="  ").grid(row=1, column=3)
        Label(master, text="文件路径：").grid(sticky=E)

        self.path = StringVar()
        self.e1 = Entry(master, textvariable = self.path, width=50).grid(row=2, column=1,columnspan=4)
        self.button1 = Button(master, text='选择训练数据集',command = self.select_file).grid(row=2, column=5)

        Label(master, text="训练模型：").grid(sticky=E)
        selfbutton2 = Button(master, text='ID3算法',width=15, command = self.train_id3_model).grid(row=3, column=1)
        self.button3 = Button(master, text='C4.5算法',width=15, command = self.train_c45_model).grid(row=3, column=3)

        Label(master, text="训练数据集：").grid(sticky=E)
        self.text1 = Text(master,height=30, width=50)
        self.text1.grid(column=1, columnspan=4)

    def select_file(self):
        self.path_ = askopenfilename()
        self.path.set(self.path_)
        self.fr = open(self.path_)
        self.lines = self.fr.readlines()
        self.text1.delete(0.0,END)
        for line in self.lines:
            self.text1.insert(END,line)
        self.fr.close()

    def train_id3_model(self):
        self.fr=self.text1.get(0.0, END)
        self.fp = 'myTainDataSet.txt'
        self.f = open(self.fp,'w')
        self.f.write(self.fr)
        self.f.close()
        self.myID3 = create_id3_model(self.master, self.fp)

    def train_c45_model(self):
        self.fr=self.text1.get(0.0, END)
        self.fp = 'myTainDataSet.txt'
        self.f = open(self.fp,'w')
        self.f.write(self.fr)
        self.f.close()
        self.myC45 = create_c45_model(self.master, self.fp)



if __name__ == '__main__':
    root = Tk()
    root.title('决策树By HUST')
    app = App(root)
    mainloop()
