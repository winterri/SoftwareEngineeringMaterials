#include<stdio.h>
#include<stdlib.h>


/*number storage*/
struct numStorage {
    int number[100];
    int top;
};


/*symbol storage*/
struct symbolStorage {
    char symbol[100];
    int top;
};


void initOperateNum(struct numStorage *StackNum)
{
    StackNum->top = -1;
}


void initOperateSymbol(struct symbolStorage *StackSymbol)
{
    StackSymbol->top = -1;
}


/*save number in number storage*/
void inNumStorage(struct numStorage *StackNum, int num)
{
    StackNum->top ++;
    StackNum->number[StackNum->top] = num;
}


/*save number in symbol storage*/
void inSymbolStorage(struct symbolStorage *StackSymbol, char ch)
{
    StackSymbol->top ++;
    StackSymbol->symbol[StackSymbol->top] = ch;
}


/*read number storage*/
int readNumStorage(struct numStorage *StackNum)
{
    return StackNum->number[StackNum->top];
}


/*read symbol storage*/
char readSymbolStorage(struct symbolStorage *StackSymbol)
{
    return StackSymbol->symbol[StackSymbol->top];
}


/*get number from number storage*/
int getNumData(struct numStorage *StackNum)
{
    int num;
    num = StackNum->number[StackNum->top];
    StackNum->top --;
    return num;
}


/*get symbol from expression storage*/
char getSymbol(struct symbolStorage *StackSymbol)
{
    char symbol;
    symbol = StackSymbol->symbol[StackSymbol->top];
    StackSymbol->top --;
    return symbol;
}


/*symbol priority judgeSymbolPriority*/
int judgeSymbolPriority(char ch) {
    if(ch == '(') {
        return 1;
    }
    if(ch == '+' || ch == '-') {
        return 2;
    }
    else if(ch == '*' || ch == '/') {
        return 3;
    }
    else if(ch == ')') {
        return 4;
    }
}


/*regular expression*/
int math(int v1, int v2, char c)
{
    int result;
    switch(c) {
        case '+' : {
            result = v1 + v2;
            break;
        }
        case '-' : {
            result = v1 - v2;
            break;
        }
        case '*' : {
            result = v1 * v2;
            break;
        }
        case '/' : {
            result = v1 / v2;
            break;
        }
    }
    return result;
}


int main()
{
    printf("Enter the expression(no blank,no decimals): ");
    struct numStorage data;
    struct symbolStorage symbol;
    initOperateNum(&data);
    initOperateSymbol(&symbol);
    int i, t, sum, v1, v2;
    char c;
    i = t = sum = 0;
    char v[100] = {0};
    char *str = (char *)malloc(sizeof(char)*200);
    while((c = getchar()) != '\n') {
        str[i] = c;
        i ++;
    }
    str[i] = '\0';
    for(i = 0; str[i] != '\0'; i ++) {
        if(i == 0 && str[i] == '-') {
            v[t++] = str[i];
        }
        else if(str[i] == '(' && str[i+1] == '-') {
            i ++;
            v[t++] = str[i++];
            while(str[i] >= '0' && str[i] <= '9') {
                v[t] = str[i];
                t ++;
                i ++;
            }
            inNumStorage(&data, atoi(v));
            while(t > 0) {
                v[t] = 0;
                t --;
            }
            if(str[i] != ')') {
                i --;
                inSymbolStorage(&symbol, '(');
            }
        }
        else if(str[i] >= '0' && str[i] <= '9') {
            while(str[i] >= '0' && str[i] <= '9') {
                v[t] = str[i];
                t ++;
                i ++;
            }
            inNumStorage(&data, atoi(v));
            while(t > 0) {
                v[t] = 0;
                t --;
            }
            i --;
        }
        else {
            if(symbol.top == -1) {        //if symbol storage is empty, put symbol in storage
                inSymbolStorage(&symbol, str[i]);
            }
            else if(judgeSymbolPriority(str[i]) == 1) { //if the symbol is"(", put symbol in symbol storage
                inSymbolStorage(&symbol, str[i]);
            }
            else if(judgeSymbolPriority(str[i]) == 2) { //if the symbol is '+' or '-'，judgeSymbolPriority symbol priority
                if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 1) { //if top element in stack is "(", put it in symbol Storage
                    inSymbolStorage(&symbol, str[i]);
                }
                else if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 2) { //if top element in stack is '+' or '-'，pop it for calculate
                    while(symbol.top >= 0 && data.top >= 1) {
                        v2 = getNumData(&data);
                        v1 = getNumData(&data);
                        sum = math(v1, v2, getSymbol(&symbol));
                        inNumStorage(&data, sum); //save temp result in numStorage
                    }
                    inSymbolStorage(&symbol, str[i]); //new symbol in storage
                }
                else if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 3) { //if top element in stack is '*' or '/'，put it in symbol storage
                    while(symbol.top >= 0 && data.top >= 1) {
                        v2 = getNumData(&data);
                        v1 = getNumData(&data);
                        sum = math(v1, v2, getSymbol(&symbol));
                        inNumStorage(&data, sum); //save temp result in numStorage
                    }
                    inSymbolStorage(&symbol, str[i]);
                }

            }
            else if(judgeSymbolPriority(str[i]) == 3) { //if the symbol is '*' or  '/'，judgeSymbolPriority symbol priority
                if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 1) { //if top element in stack is '('，put it in symbol Storage
                    inSymbolStorage(&symbol, str[i]);
                }
                else if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 2) { //if top element in stack is '+' or '-'，put it in symbol Storage
                    inSymbolStorage(&symbol, str[i]);
                }
                else if(judgeSymbolPriority(readSymbolStorage(&symbol)) == 3) { //if top element in stack is '*' or '/'，pop it for calculate
                    while(symbol.top >= 0 && data.top >= 1) {
                        v2 = getNumData(&data);
                        v1 = getNumData(&data);
                        sum = math(v1, v2, getSymbol(&symbol));
                        inNumStorage(&data, sum); //save temp result in numStorage
                    }
                    inSymbolStorage(&symbol, str[i]);
                }
            }
            else if(judgeSymbolPriority(str[i]) == 4) { // if symbol is ')'，pop it for calculate until get '('
                do {
                    v2 = getNumData(&data);
                    v1 = getNumData(&data);
                    sum = math(v1, v2, getSymbol(&symbol));
                    inNumStorage(&data, sum); //save temp result in numStorage
                }while(judgeSymbolPriority(readSymbolStorage(&symbol)) != 1);
                getSymbol(&symbol); //pop '(' after the calculate done in bracket
            }
        }
    }
    free(str); //release memory
    while(symbol.top != -1) {
        v2 = getNumData(&data);
        v1 = getNumData(&data);
        sum = math(v1, v2, getSymbol(&symbol));
        inNumStorage(&data, sum);
    }
    printf("The result is : ");
    printf("%d", data.number[0]);

    getchar();
    system("pause");
    return 0;

}