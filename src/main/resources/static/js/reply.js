async function get1(bno){
    const result = await axios.get(`/replies/list/${bno}`)
    consol.log("서버로 부터 받은 result 확인 : " + result)
}