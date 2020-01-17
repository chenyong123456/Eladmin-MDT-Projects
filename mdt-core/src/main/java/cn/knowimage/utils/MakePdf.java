package cn.knowimage.utils;


import cn.knowimage.ims.vo.MakePdfPojo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.Base64;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.Charset;


public class MakePdf {

    public static final String TESTEVSCHOP = "iVBORw0KGgoAAAANSUhEUgAAAQsAAACbCAYAAAB1RL6lAAAACXBIWXMAAAsTAAALEwEAmpwYAAAKTWlDQ1BQaG90"
            + "b3Nob3AgSUNDIHByb2ZpbGUAAHjanVN3WJP3Fj7f92UPVkLY8LGXbIEAIiOsCMgQWaIQkgBhhBASQMWFiApWFBURnEhVxILVCkidiOKgKLhnQYqIWotVXDjuH9yntX167+3t+9f7vOec5"
            + "/zOec8PgBESJpHmomoAOVKFPDrYH49PSMTJvYACFUjgBCAQ5svCZwXFAADwA3l4fnSwP/wBr28AAgBw1S4kEsfh/4O6UCZXACCRAOAiEucLAZBSAMguVMgUAMgYALBTs2QKAJQAAGx5fE"
            + "IiAKoNAOz0ST4FANipk9wXANiiHKkIAI0BAJkoRyQCQLsAYFWBUiwCwMIAoKxAIi4EwK4BgFm2MkcCgL0FAHaOWJAPQGAAgJlCLMwAIDgCAEMeE80DIEwDoDDSv+CpX3CFuEgBAMDLlc2"
            + "XS9IzFLiV0Bp38vDg4iHiwmyxQmEXKRBmCeQinJebIxNI5wNMzgwAABr50cH+OD+Q5+bk4eZm52zv9MWi/mvwbyI+IfHf/ryMAgQAEE7P79pf5eXWA3DHAbB1v2upWwDaVgBo3/ldM9sJ"
            + "oFoK0Hr5i3k4/EAenqFQyDwdHAoLC+0lYqG9MOOLPv8z4W/gi372/EAe/tt68ABxmkCZrcCjg/1xYW52rlKO58sEQjFu9+cj/seFf/2OKdHiNLFcLBWK8ViJuFAiTcd5uVKRRCHJleIS6"
            + "X8y8R+W/QmTdw0ArIZPwE62B7XLbMB+7gECiw5Y0nYAQH7zLYwaC5EAEGc0Mnn3AACTv/mPQCsBAM2XpOMAALzoGFyolBdMxggAAESggSqwQQcMwRSswA6cwR28wBcCYQZEQAwkwDwQQg"
            + "bkgBwKoRiWQRlUwDrYBLWwAxqgEZrhELTBMTgN5+ASXIHrcBcGYBiewhi8hgkEQcgIE2EhOogRYo7YIs4IF5mOBCJhSDSSgKQg6YgUUSLFyHKkAqlCapFdSCPyLXIUOY1cQPqQ28ggMor"
            + "8irxHMZSBslED1AJ1QLmoHxqKxqBz0XQ0D12AlqJr0Rq0Hj2AtqKn0UvodXQAfYqOY4DRMQ5mjNlhXIyHRWCJWBomxxZj5Vg1Vo81Yx1YN3YVG8CeYe8IJAKLgBPsCF6EEMJsgpCQR1hM"
            + "WEOoJewjtBK6CFcJg4Qxwicik6hPtCV6EvnEeGI6sZBYRqwm7iEeIZ4lXicOE1+TSCQOyZLkTgohJZAySQtJa0jbSC2kU6Q+0hBpnEwm65Btyd7kCLKArCCXkbeQD5BPkvvJw+S3FDrFi"
            + "OJMCaIkUqSUEko1ZT/lBKWfMkKZoKpRzame1AiqiDqfWkltoHZQL1OHqRM0dZolzZsWQ8ukLaPV0JppZ2n3aC/pdLoJ3YMeRZfQl9Jr6Afp5+mD9HcMDYYNg8dIYigZaxl7GacYtxkvmU"
            + "ymBdOXmchUMNcyG5lnmA+Yb1VYKvYqfBWRyhKVOpVWlX6V56pUVXNVP9V5qgtUq1UPq15WfaZGVbNQ46kJ1Bar1akdVbupNq7OUndSj1DPUV+jvl/9gvpjDbKGhUaghkijVGO3xhmNIRb"
            + "GMmXxWELWclYD6yxrmE1iW7L57Ex2Bfsbdi97TFNDc6pmrGaRZp3mcc0BDsax4PA52ZxKziHODc57LQMtPy2x1mqtZq1+rTfaetq+2mLtcu0W7eva73VwnUCdLJ31Om0693UJuja6UbqF"
            + "utt1z+o+02PreekJ9cr1Dund0Uf1bfSj9Rfq79bv0R83MDQINpAZbDE4Y/DMkGPoa5hpuNHwhOGoEctoupHEaKPRSaMnuCbuh2fjNXgXPmasbxxirDTeZdxrPGFiaTLbpMSkxeS+Kc2Ua"
            + "5pmutG003TMzMgs3KzYrMnsjjnVnGueYb7ZvNv8jYWlRZzFSos2i8eW2pZ8ywWWTZb3rJhWPlZ5VvVW16xJ1lzrLOtt1ldsUBtXmwybOpvLtqitm63Edptt3xTiFI8p0in1U27aMez87A"
            + "rsmuwG7Tn2YfYl9m32zx3MHBId1jt0O3xydHXMdmxwvOuk4TTDqcSpw+lXZxtnoXOd8zUXpkuQyxKXdpcXU22niqdun3rLleUa7rrStdP1o5u7m9yt2W3U3cw9xX2r+00umxvJXcM970H"
            + "08PdY4nHM452nm6fC85DnL152Xlle+70eT7OcJp7WMG3I28Rb4L3Le2A6Pj1l+s7pAz7GPgKfep+Hvqa+It89viN+1n6Zfgf8nvs7+sv9j/i/4XnyFvFOBWABwQHlAb2BGoGzA2sDHwSZ"
            + "BKUHNQWNBbsGLww+FUIMCQ1ZH3KTb8AX8hv5YzPcZyya0RXKCJ0VWhv6MMwmTB7WEY6GzwjfEH5vpvlM6cy2CIjgR2yIuB9pGZkX+X0UKSoyqi7qUbRTdHF09yzWrORZ+2e9jvGPqYy5O"
            + "9tqtnJ2Z6xqbFJsY+ybuIC4qriBeIf4RfGXEnQTJAntieTE2MQ9ieNzAudsmjOc5JpUlnRjruXcorkX5unOy553PFk1WZB8OIWYEpeyP+WDIEJQLxhP5aduTR0T8oSbhU9FvqKNolGxt7"
            + "hKPJLmnVaV9jjdO31D+miGT0Z1xjMJT1IreZEZkrkj801WRNberM/ZcdktOZSclJyjUg1plrQr1zC3KLdPZisrkw3keeZtyhuTh8r35CP5c/PbFWyFTNGjtFKuUA4WTC+oK3hbGFt4uEi"
            + "9SFrUM99m/ur5IwuCFny9kLBQuLCz2Lh4WfHgIr9FuxYji1MXdy4xXVK6ZHhp8NJ9y2jLspb9UOJYUlXyannc8o5Sg9KlpUMrglc0lamUycturvRauWMVYZVkVe9ql9VbVn8qF5VfrHCs"
            + "qK74sEa45uJXTl/VfPV5bdra3kq3yu3rSOuk626s91m/r0q9akHV0IbwDa0b8Y3lG19tSt50oXpq9Y7NtM3KzQM1YTXtW8y2rNvyoTaj9nqdf13LVv2tq7e+2Sba1r/dd3vzDoMdFTve7"
            + "5TsvLUreFdrvUV99W7S7oLdjxpiG7q/5n7duEd3T8Wej3ulewf2Re/ranRvbNyvv7+yCW1SNo0eSDpw5ZuAb9qb7Zp3tXBaKg7CQeXBJ9+mfHvjUOihzsPcw83fmX+39QjrSHkr0jq/da"
            + "wto22gPaG97+iMo50dXh1Hvrf/fu8x42N1xzWPV56gnSg98fnkgpPjp2Snnp1OPz3Umdx590z8mWtdUV29Z0PPnj8XdO5Mt1/3yfPe549d8Lxw9CL3Ytslt0utPa49R35w/eFIr1tv62X"
            + "3y+1XPK509E3rO9Hv03/6asDVc9f41y5dn3m978bsG7duJt0cuCW69fh29u0XdwruTNxdeo94r/y+2v3qB/oP6n+0/rFlwG3g+GDAYM/DWQ/vDgmHnv6U/9OH4dJHzEfVI0YjjY+dHx8b"
            + "DRq98mTOk+GnsqcTz8p+Vv9563Or59/94vtLz1j82PAL+YvPv655qfNy76uprzrHI8cfvM55PfGm/K3O233vuO+638e9H5ko/ED+UPPR+mPHp9BP9z7nfP78L/eE8/sl0p8zAAAAIGNIU"
            + "k0AAHolAACAgwAA+f8AAIDpAAB1MAAA6mAAADqYAAAXb5JfxUYAABCuSURBVHja7J1tjtvGHcYfeW3HdJpKCdp+SD+IcdoPNQqscoKVT7DyCaQ9gekTmHuC1Z7A2hNEewJT6AEsAUX6kj"
            + "ctkBQt4jaUnZiuU1v9oGE0O5rhDCVq9fb8AMFeiiKHw5lnnv+f5LA0Ho9BCCE2rrEKCCEUC0IIxYIQQrEghFAsCCEUC0IIxYIQQigWG8aLvz1/j7VAVkGJN2VtJj999b+9G3euv2FNEDo"
            + "LMsNfvvvXzfT/N+5cf/P5ixfXXX978ewL53P9zbMv91jbhM6CEEJnQQihWBBCKBaEEIoFIYRiQQihWBBCCMWCEEKxIIRQLAghFAtCCMWCEEKxIIRQLAghhGJBCKFYEEIoFoQQigUhhGJB"
            + "CKFYEEIoFoQQQrEghFAsCCEUC0IIxYIQQrEghFAsCCEUC0IIoVgQQigWhBCKBSGEYkEI2RSub/oBeJ7nslpd+TvawEOtAKiJjy+WBUvYTw1AQ/q7D6C7pGNqiP2ldMX+0u+6CxxDRfp7K"
            + "D6FkyQJPM+riTYWJUnSp1hsLnUAT5RlpQ08jgDAI2VZe85OUBHbizTC2dDsp7TEYzrQlK0NYB/AGYBWxu+74vj70ietF3m7xwDCnKLsQh9ALMr4QAxeIwBBkiQdisXuEmka9iL0FMcTAD"
            + "jJuY2vLd+fKy5BFYRHUllayxp9czoCWaiaokO2DZ36UFn2UQHHoBNlE/dEu5DruKy4GorFmtnzLDvua5ZljTKxoXFexXFc1TZVATkQx71q0pG6KS07ER2ybzmG0SrEzvO8BoCq2oY8z6t"
            + "rVh8mSTKkWKzPaOSCbf02thdfMyKfFygWjRw23teEjJFhtA+UMjY0Ickq0Dm3x45ukmKxpcRL3l5H6ii+psENNA4qje1lTqWOoytzyyC4UYYTiyzhWWjJQ7hyYPhtU6kfXQjSVByJOjhk"
            + "DRBZOY2HkkOrpLkJ6e/mrnQAisViI0jWum2NPZUbZztnPmQfs4laHQ+UBg1ME5SmkK2aUVag2FxNEQRXfN4PJGewqnJQLJbEsWJ1mzm/n5e6GKEOMmxoC6tLJAaYJOA2mcoadVL1PF9I5"
            + "9ZXBPgCs3kXisVV8ivg2rPZxaHSgZs5v5+HVkaseiEaeHeFVeWvqJM9zOgkLaXuz0S44YvvOlLnizIEr2fpxANLGFmUeHeSJAkBwPO8tuLwWkmSRBSLFfHVb/9Yega81Xw1tvx0vKTOqD"
            + "IS4UboIDTp77sFjf5HohPEUn6jnJHfMHVgYHKJcN6O1s/IedQ124okd3YgBCTMsP66pOFY46iuoqPWDP9nGLJq7nz75zHc7uBcFTXHUWsorVdUmDCUOkgdswnBkeiEsaUDY8GOZsqzpEK"
            + "qhhhqmNEUZQykjh9KFj9co/Ptm9zNpruKbc5ZFB3nn8z5268d1nmI5V+qjUQoVFWOK15hvZY1QlozhBlp/TREh+wIB1QR4lZXBEfn3OqW8sQFnId9z/MqGpc5oLNYX3pK49nP+T0sja9I"
            + "KjmOxeZislxJB5cvH7agv4zq53AW0QIj+0AjFj5mL3GeSesFmnyEyz02rjmpIkS7rglBIorF+lJX/v8k5/frRFGXLVOxGIk8RfOKypAmF1VR1rka3eXbcI3PzZH4V05sN7AdDy7ujFhEl"
            + "pHb9j0sjbpIrioUGGKS0Owb3MMyw7jIIMq2TnSM1T+vktU+hkmSROLKR1kSC9nhjZIk6VIsNnc0zjNSth3taYjlPK1ZVBiSdtxlj9Z5hejC4CguNGISWDpvgNlE7s+dFtk31plEe9/hGG"
            + "SnVtZ8B4rF9hJg+vjztoQhakhiGtVbcL906hJKDKXtmhxP1eD+nmDy3Eob+ofJ1HIfZnxfhjlPs+i56GSEdSHFYrs5EZ+NfvDHEpKYhLBuCdts7gaGkXU/xzbPpfIdik/WVaMA+itWqmt"
            + "pCmfSynASbalsbU25U4fyc/lFKKJzSGeb/JTprojFPaUBnziOlG1H27kM6+5LHaXiOLJl3ZlYER0/xtXdZlzLYeNjMSIfGEbjlkPuwM/Yxqk4n30lNDgUy1oGwYoxnaNi3+BQnmCS3OwA"
            + "gOd5HYM7anie19qWiXC2QSxijfJHDp2zk9FhU/qGTlhTRkcf+R4009nYecKNfUfLXMLsdHnI4SxsVror6ipPHbQyyh9qxGGonKsww/r3pPxMA7OJ1apY1hOC0tWInq2DPwbgi/ks9jNCn"
            + "8dinWDTXUZpPB5vtFI4zMFZh/7SaE9pfLqR4T70t0M/dijaCO73aERY7pOdJegTsEVxLDqXehPaGYp7SO8jTO/uzNrmQHJUrudsJMofCHHpKG7kQmyji8XusD1Onx2hs1hP+o6WHo4Ope"
            + "soFlGOMsZbUM+hIawbKufi04z6NnXE9GnOLrKTmKY5OztS2FM2OIDUMX5qcEGRwaXoylAzuI3KJp/gXXgVQCzi17y0DZ04htvtu+0c+2qI0d/20eVm5vndMog0HbwvRCT9dEVnUjmCfWL"
            + "etNMODEJz32EbdcPv5akDLjRli6RjPBL7023nYZIkLbGfM019hJvckXYhDJFHvgbsCcyeGIE6lhxDM2MUDLCc6+tjjVi4OhjXnMW8QhFhOuNWGdmzT8n1dyTVtS5cUMMKH5eTlqfQPxBn"
            + "y5e0xTYuRLljTdh6lNEO5PUuADTU1wB4nhdgmly/t+kPk+2SWBSJD/3NR0Ms996MrmJlW1iPe0HUumllODPZeXU1YudLDqFvEN0apk+fLnL8LVx+hYDs9IawX0UKASArD+F5ni+EpI0Nh"
            + "2LhyLdvb15/5/VbvHvjD8B4jDe3X+P5D/8c37r+PfYmVfh2bwzsvR3Du3l9DLITJEmyM8dKsSDOvHj1qvTerVvj737xpvTrH/Z+bjjJm1clb++WsSE9e/2qdEtMUfT8Rgkf7r1zad3kp1"
            + "cl78atMcWCYkGxIEZe/viidPvd9za2Ee6SWPDFyGSlbLJQ7BoUC0IIxYIQQrEghFAsCCEUC0IIxYIQQrEghBCKBSGEYkEIoVgQQigWhBCKBSGEYkEI2XL4kqEpPiYzO8WW9QJcnq2qjXx"
            + "TugViX4GlLL709xD5Z4SqYDKjVB3Tqfp16wSYzhY1XLAO031C7DfC/C8Frkn1HCvlb4llQxT/TpSaqPv03w625MXGi8L5LCaEmL7At2FZd955MOui4aWvHMh6w1aEy7OP530zWheXZ8FW"
            + "55k0rTcS680jGnXMznw9gP7FQy6MDcdfAfB9QWXWMcTl10KYZgwHsFvzWdBZXG4ch0I02nCfibll6MixIgYdpRGeiFGxiFGronRKteNUxf7bygh6qKkL2dXkcTQRZqfz3xfbGuaoS9u6D"
            + "U095xGKGPne/dFExntKxGB1f1velE5nkW8UBoBPADwton4tI+9ICn8WcRa6bReBvN+8ncyVc0kAxg7l6OPyDO2nlpDO5gyLYKNfHkRn4U46mpUVF1A0ESbT48tvBStjOrX9ulNe0nbTvI"
            + "TvGOrsa5ZFjueYUCwWIhYhx4lin88MDdj15cRhhnOReSq5mf4OnwdfybFUFUGpG0LDfcdtD6V6rkjnSD2fx4r4VDD7lrKREg71KRa7Q1uc/APREELRSFze02lrrJWcI+y8ruUTZCdn60r"
            + "HuLA4qD6yX5Q0EGWuYvp6QblzVpV9DQ3f6Y6/qqnjRcMs11DtEezvhC2r2/I87zxJkgbFYjcIRGdrS25jnbDZ7Qbyvfi4alm/ZxGLQNRVVXwiTF/6Eyrb7kj1GRr2W9vw9lPZ9g6y62LR"
            + "0DTSNL6tFbDtOGc4ZOJAjGTHayRigeKq0qsGZwWEIS70JKc0UMoycAxPig5nKRZb7iYOLA1xXh6IBlTX7PNEWXYqRKpjiX8f4fLr/ZbZWGNNnF52cALNnNvta8TiTNnOhRKenCoh11ARh"
            + "9gQqt2ThPxBAXXUk8S7T7EgRdLSCMVANPYT0YCPLdtoStuSBahi+W3enEUaMnRE+SpKWNE0CMlI03ll2tDfjHag7LepiEGASTI4zX9U53ApkVQXhbDpLzumWCxOW4wU844+AxHvdxWheK"
            + "wZrQNlPZe8Q1N0iDTUOZmjjFXHfUW4nLysG9zDhRCUEPobp2whSHoFRE2Wyg7kvthW17I9VUwiNmmKRRGiEImOLDeurmiU84pFrOQWdEIhr1dWLLivjLRqCJCOxFGRo+QCuYWy+PREnQa"
            + "GUCOrc/uiLnTCUhEClIZgan01FKHOi0mgdOXYp1jsJunoX7fY2kUIM0bwqrLfgcZppJ2toxGc9Hbq3hLrSO28HSFSHczmdUIlt5CHmthuDbNJ3H3M3usgC01ZcRG1OVxWFYRiUSC9nCNO"
            + "HfkuZwYZlr2jCQGOhJDUJfGrFOi6UtFqQH9LtZqrCDO2FVtyOfPcXn+K2WTvPGJBtkEs3n72n/K1ux+M1qQ4ea+QRIYQIsvphJZOleYsjjCboDws8FgjSSxqDsdezljHZt39OcqX3jlbV"
            + "cSri3zPijAM2RaxuHb3gxG+ernXf/+HSu393/z7inZbKTAX0JXcgO36f1k09KFFMNrYvkt1WXUzEsdbE3U0gv6qSmfOfTMMcemLG1HKO7ffXKFQpHHwI0MYovsMLGKRhgxdTQc5UzpFw6"
            + "F8rkLRE9u0LRtgei/DqogNdQIp1KqL79OwqKwJd+ZxgGTbchafv3r5zu9v3f7vFeyqXFAYktr59CGxUNNBWmKdx5jOWlUUdcw+7q5bFljyK21kX36swHyDW8q5EMuhoY4aSl6jaRDJtPy"
            + "qCzmG/s7bkXLsab33MkKiqsbVaEXb87x0m/0kSWKKxZpwRUKxjBHTJgAdTC/Xrusx6MSiJsSu5ZCXORSfVDTU43U5dl/8bt8QprQ0oUlfEop5HkbLysUcYHp5fZ1uxadYLDE/4eoYTKNR"
            + "DYvP9zCvUPgZsXqkGWl1y9qYvYrSkkbxENN5KdNPfc5jTkXjsRCODuw3WUE4j45hn2VkX1olFItC0OUIRqJhPtDY+gpmJ7ctr7D8vmXkc1mmSyyqSb823K+2HIv6C0T9Vi3CUXLY5qcZ4"
            + "U1WufoLijER8FUAk1FNTu4NMJ0R2+QAUjdyYOh80RbWk4vF7mGSnwkxfZ7Dx+Q27XPDb1yfUr2P2cTsEbIvkw4k19UXomT7HGuOybh+kiTpJ9z2jkJnMen8DUxuCkqFIkb2pdMuzE9Xns"
            + "E8a/eyyr/MOzj70r+nGreV3tvQyRDJNEfhSzmOquRYXEW9IfIOI3F++lIZykr+IhUrOgqKReEd4hPRwGJp2bEhp9DRxMIxzNl+eT89BwcS5XAqfVzd8yGh6LBDTN8JksdFDcU2QiEYFZi"
            + "Tv+dSHiWS/r0v/pXPR6XgttBzrHsAwGc//sO7++6HW/9OgI2f3ZuQdeTL1y9vfnzz9mvj98BNAPgYeM2cBSE7wNd4oU3OXrt27Q2dBSFkJ6GzIIRQLAghFAtCCMWCEEKxIIRQLAghFAtC"
            + "CKFYEEIoFoQQigUhhGJBCKFYEEIoFoQQigUhu8nfn3/rsRam8BF1QjR88fz7m7/75fuvWRMUC0IIwxBCCMWCEEKxIKRo/vTN0z3WQnEwZ0EIobMoiqejmCMUobOgsyCE0FkQQigWhBCKB"
            + "SGEYkEIoVgQQigWhBBCsSCEUCwIIRSLreD587/eYC2QTYF3cBJC6CwIIRQLQgjFghBCsSCEUCwIIRQLQgihWBBCKBaEEIoFIYRiQQhZS/4/AAIRgR3GjE4GAAAAAElFTkSuQmCC";



    public static String getHtml() {

        StringBuffer html = new StringBuffer();
        html.append("<b><center><div style='color=red;margin-left=50px'>你好世界！hello world !</div></center></b>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;申请科室:&nbsp;cascascas");
        html.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;申请会诊日期:&nbsp;casn年cas月cas日</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;患者姓名:&nbsp;cascascas");
        html.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;性别:&nbsp;casn&nbsp;&nbsp;年龄:&nbsp;cas&nbsp;&nbsp;住院号:&nbsp;cas</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;病情简介:&nbsp;cascascas</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;初步诊断:&nbsp;cascascas</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");
        html.append("<div style='color=red;margin-top=350px'><hr/></div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;建议进一步需做检查:&nbsp;cascascasscxsacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                "csaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</div>");

        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;会诊结果:&nbsp;cascascasscxsacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                "csaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;治疗方案:&nbsp;cascascasscxsacccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc" +
                "csaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;会诊专家小组成员代表签字:&nbsp;cascascasscxsacccccccccccccc</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-right=300px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;河南圣德医院多学科会诊中心</div>");
        html.append("<div style='color=red;margin-right=300px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;2019&nbsp;&nbsp;年&nbsp;&nbsp;08&nbsp;&nbsp;月&nbsp;&nbsp;21&nbsp;&nbsp;日</div>");
        html.append("<div style='color=red;margin-top=350px'>&nbsp;</div>");

        html.append("<div style='color=red;margin-left=50px'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
                "&nbsp;&nbsp;&nbsp;&nbsp;(会诊结果患者或法定代表人有知情权和选择权，执行结果记录入病历中。)</div>");
        return html.toString();
    }

    public static void createPdf(MakePdfPojo makePdf) {
        try {
            //BaseFont bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H", false);
            //直接引用 下载好的字体文件
            BaseFont bfChinese = BaseFont.createFont("C:\\Users\\wh123\\Desktop\\MDTProject\\src\\main\\resources\\font\\SimSun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            Font font = new Font(bfChinese, 12, Font.NORMAL);
            Document document = new Document(PageSize.A4, 10, 10, 10, 10);
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\wh123\\Desktop\\test.pdf"));
            document.open();

            // document.add(new Paragraph("创建pdf文件.支持中文......", font));

            String s = getHtml();//html 标签内容
            ByteArrayInputStream bin = new ByteArrayInputStream(s.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, bin, Charset.forName("UTF-8"), new ChinaFontProvide());

            //将图片合成pdf
            Image image = Image.getInstance("C:\\Users\\wh123\\Desktop\\002.png");

            image.scalePercent(10);// 控制图片大小
            image.setAbsolutePosition(200, 500);
            //document.add(image);

            //base64 编码的图片类型
            Image image2 = Image.getInstance(Base64.decode(TESTEVSCHOP));

            image2.scalePercent(10);// 控制图片大小
            image2.setAbsolutePosition(400, 500);
            //document.add(image2);

            document.close();
            mPdfWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 该类为静态的常用类
     * 提供中文, 实现了FontProvider接口
     */
    public static final class ChinaFontProvide implements FontProvider {

        @Override
        public Font getFont(String arg0, String arg1, boolean arg2, float arg3,
                            int arg4, BaseColor arg5) {
            BaseFont bfChinese = null;
            try {
                //bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
                //直接引用 下载好的字体文件
                bfChinese = BaseFont.createFont("C:\\Users\\wh123\\Desktop\\MDTProject\\src\\main\\resources\\font\\SimSun.ttf", BaseFont.IDENTITY_H,BaseFont.NOT_EMBEDDED);

            } catch (Exception e) {
                e.printStackTrace();
            }
            Font FontChinese = new Font(bfChinese, 12, Font.NORMAL);
            return FontChinese;
        }

        @Override
        public boolean isRegistered(String arg0) {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println("begin");
        createPdf(new MakePdfPojo());
        System.out.println("end");
    }

}
