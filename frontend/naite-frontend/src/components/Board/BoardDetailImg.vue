<template>
    <div id='boarddetailimg'>               
        <img :src="boardImg[0]" alt="" style='max-width:250px' class='webImg' @click='zoomInImg()'>
        <img :src="boardImg[0]" alt="" style='max-width:100px' class='mobileImg' @click='zoomInImg()'>
        <div v-if='boardImg[1]'>
            <div @click='zoomInImg()'>
                <img :src="boardImg[1]" alt="" style='margin-top:10%;margin-left:10%'>
                <div class='hoveredBox'>{{boardImg.length-1}}장 더보기...</div>
            </div>

        </div>
        <div v-if='hoveredImage' class='hvdimg'>
            <header>
                <div @click='zoomOut'>
                    <i class="fas fa-times-circle"></i>
                </div>
            </header>
            <body>
                <div>
                    <img :src="boardImg[i]" alt="">
                </div>
            </body>

        </div>
        <div v-if='hoveredImage'>
            <button @click='imgMove(-1)'>
                <i class="fas fa-arrow-circle-left"></i>
            </button>
            <button @click='imgMove(1)'>
                <i class="fas fa-arrow-circle-right"></i>
            </button>

        </div>
        
    </div>
</template>
<script>
export default {
    name:'BoardDetailImg',
    props:{
        boardImg:[Object,Array]
    },
    data:function(){
        return{
            hoveredImage:'',
            i:0,
        }
    },
    methods:{
        imgMove:function(change){
            this.i = this.i+change
            if (this.i <0) {
                this.i=0
            } else if (this.i >= this.boardImg.length) {
                this.i = this.boardImg.length-1
            }

        





        },
        zoomOut:function(){
            this.hoveredImage = ''
        },
        zoomInImg:function(){            
            this.hoveredImage = this.boardImg
        },
        loadingImg:function(){
            if (this.boardImg.length>1){  
                let firstImg =document.querySelector('.webImg')
                if (screen.width < 501) {
                    firstImg =document.querySelector('.mobileImg')
                }
                const secondImg = document.querySelector('#boarddetailimg > div > div >img')
                const hoberdImg = document.querySelector('.hoveredBox')
                
                secondImg.style.width = `${firstImg.clientWidth/2}px`
                hoberdImg.style.width = `${firstImg.clientWidth/2}px`
                hoberdImg.style.height = `${secondImg.clientHeight}px`
                hoberdImg.style.bottom ='0px'

                const paddingTop = secondImg.clientHeight/2 - secondImg.clientHeight*0.1
                hoberdImg.style.paddingTop = `${paddingTop}px`
                hoberdImg.style.color = 'black'
                hoberdImg.style.fontFamily='font1'                
            }
        }
    },
    watch:{
        boardImg:function(){
            setTimeout(() => {                
                this.loadingImg()
            }, 100);
        }
    },
    mounted(){
        setTimeout(() => {                
                this.loadingImg()
        }, 100);
    },
    updated(){
        setTimeout(() => {                
                this.loadingImg()
            }, 100);
    },
    created(){
        setTimeout(() => {                
                this.loadingImg()
            }, 100);
    }
    
}
</script>
<style>
#boarddetailimg{
    position:relative;
    width: 100%;
    background-color: teal;
    padding: 1%;
    display: flex;
    align-items: flex-end;    

    
}
#boarddetailimg img{
    cursor: pointer;
    
}

#boarddetailimg > img :nth-child(1) {
    position: relative;
    left:0;
    top:0;   
    cursor:pointer; 
}
.mobileImg{
    display:none;
}
#boarddetailimg > div:nth-of-type(1) > div{
    position: relative;
    cursor:pointer;
}


.hoveredBox {
    position:absolute;
    background-color: rgba(255, 255, 255, 0.459);    
    top:0;    
    margin-top: 10%;
    margin-left: 10%;
    padding-top: auto;
    cursor:pointer;

}

.hvdimg{
    position:fixed;
    width:768px;
    height: 484px;
    top:50%;
    left:50%;
    z-index: 1;
    transform: translate(-50%,-50%);
    background-color: rgba(255, 255, 255, 0.945);
    border-radius: 10px;
    overflow: hidden;
}
.hvdimg>header{
    position:relative;
    width:100%;
    height:5%;
    padding:10px;
    /* float: right; */
    margin-bottom: 20px;
    
}
.hvdimg>header>div>i{
    position:absolute;
    right:2%;
    font-size: 30px;
    display: initial;
    color:black;    
}
.hvdimg>header>div>i:hover{
    color:red
}
.hvdimg>body{
    position:relative;
    height:95%;
    background-color: rgba(128, 128, 128, 0.76);
    
}

.hvdimg>body>div {
    width:100%;
    height:100%;
    display:flex;
    flex-direction: column;
    justify-content: center;
    
}

.hvdimg>body>div>img{
    display: block;
    margin:0px auto;
    max-height:454px;
    width:auto;
    object-fit: cover;
}

.hvdimg + div {
    position: fixed;
    display: flex;
    justify-content: space-between;
    top:50%;    
    left:50%;
    width:40%;
    z-index:3;
    transform: translate(-50%,-50%);
}
.hvdimg + div> button {
    border: none;
    outline: none;
    background-color: transparent;
    font-size: 50px;
    color:white;
    transition: 0.3s;
}
.hvdimg + div > button:hover{
    color:greenyellow;
    font-size: 55px;

}


@media screen and (max-width:501px) {
    
    .webImg {
        display: none;
    }
    .mobileImg {
        display:initial;
    }
    .hoveredBox{
        font-size: 10px;
    }
    .hvdimg{
        position:fixed;
        width:80%;
        height: 50%;
    }

    .hvdimg>header>div>i{
        position:absolute;
        right:2%;
        font-size: 30px;
        display: initial;
        color:black;    
    }
    .hvdimg>header>div>i:hover{
        color:red
    }
    .hvdimg>body{
        position:relative;
        height:95%;
        background-color: rgba(128, 128, 128, 0.76);
        
    }

    .hvdimg>body>div {
        width:100%;
        height:100%;
        display:flex;
        flex-direction: column;
        justify-content: center;
        
    }

    .hvdimg>body>div>img{
        display: block;
        margin:0px auto;
        max-height:454px;
        width:auto;
        object-fit: cover;
    }

    .hvdimg + div {
        position: fixed;
        display: flex;
        justify-content: space-between;
        top:50%;    
        left:50%;
        width:80%;
        z-index:3;
        transform: translate(-50%,-50%);
    }
    .hvdimg + div> button {
        border: none;
        outline: none;
        background-color: transparent;
        font-size: 50px;
        color:white;
        transition: 0.3s;
    }
    .hvdimg + div > button:hover{
        color:greenyellow;
        font-size: 55px;

    }
}
</style>