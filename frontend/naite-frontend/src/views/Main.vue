<template>
    <div id="main">
        <Navbar />
        <Message />
    </div>
    
</template>
<script>
import Navbar from '@/components/Navbar'
import Message from '@/components/Message'


export default {
    name:'Main',
    components:{
        Navbar,
        Message
                
    },
    data: function() {
        return {
        }
    },
    methods:{
    },
    computed: {

    },
    
    
    created:{
        
    }
    
}
</script>

<style>

</style>