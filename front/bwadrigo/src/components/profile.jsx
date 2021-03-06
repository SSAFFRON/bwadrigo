import axios from 'axios';
import { useCallback, useState, useEffect } from 'react';
import {Button, Container, Figure, Row, Col, Table} from "react-bootstrap";
import { useNavigate } from 'react-router-dom';
import "./profile.css";

function Profile() {
    localStorage.setItem("tab", "/profile")

    const [isLogin, setIsLogin] = useState(true);
    const navigate = useNavigate();
    // const memberName = localStorage.getItem("memberName");
    const [member, setMember] = useState({
        memberName : localStorage.getItem("memberName")
    });

    function setCookie(token){
        document.cookie = token + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
    }

    function memberPlan(e) {
        window.location.href = "/member-plan"
    }

    const logout = useCallback(
        async(e) =>{
        let Authorization = localStorage.getItem("authorization")
        let RefreshToekn = localStorage.getItem("refreshtoken")
        await axios({
            method:"delete",
            url:"/v1/api/member/logout",
            headers : {
                "Authorization" : Authorization,
                "RefreshToken" : RefreshToekn 
            }
            
        }).then((res) => {
            localStorage.removeItem("memberName")
            localStorage.removeItem("authorization");
            localStorage.removeItem("refreshtoken");
            window.location.href = "/"
        })

    })


    useEffect(()=>{
        let Authorization = localStorage.getItem("authorization")
        let RefreshToekn = localStorage.getItem("refreshtoken")
        let url = "/v1/api/member"
        async function getMember(){
            
            await axios({
                method : "get",
                url : url,
                headers : {
                    "Authorization" : Authorization,
                    "RefreshToken" : RefreshToekn 
                }
            }).then((res) => {
                console.log(res)
                setMember(res.data)
            }).catch((err)=>{
                console.log(err);
            }
            )
        }
        getMember();
    },[])

    return(
        <>
            <div>
                

            {member.memberName?
            
            <div>
                <Container className='profile_container'>
                    <br></br>
                    <h2>{member.memberName}???<br/> ??????????????? ?????? ???????????? ????????? ???????????????!</h2>
                    {/* <h2>???????????? ????????? ???????????????!</h2> */}
                    {/* <Button variant="success" href="/payHistory">????????????</Button> */}
                    
                    <div className='profile_div'></div>
                </Container>
                <Container className='profile_container'>
                    <Row>
                        <Col>
                            <Figure>
                                <a href='/payHistory'>
                                    <Figure.Image
                                        width={60}
                                        height={120}
                                        src={require("../img/nav/payment1.png")}
                                        href="/"
                                    />
                                </a>
                                <Figure.Caption>
                                    ????????????
                                </Figure.Caption>
                            </Figure>
                        </Col>
                        <Col>
                            <Figure>
                                <a href='/myInfo'>
                                    <Figure.Image
                                        width={60}
                                        height={120}
                                        src={require("../img/nav/myinfo.png")}
                                    />
                                </a>
                                <Figure.Caption>
                                    ??? ??????
                                </Figure.Caption>
                            </Figure>
                        </Col>
                        <Col>
                            <Figure>
                                <a onClick={logout}>
                                    <Figure.Image
                                        width={60}
                                        height={120}
                                        src={require("../img/nav/logout.png")}
                                    />
                                </a>
                                <Figure.Caption>
                                    ????????????
                                </Figure.Caption>
                            </Figure>
                        </Col>
                    </Row>
                </Container>
                
                <Table>
                    <tbody>
                    
                        <tr>
                            <th></th>
                        </tr>

                        <tr className='profile_href' onClick={memberPlan}>
                            <th>???????????? ????????? &lt;-??????!</th>
                        </tr>
                    
                    </tbody>
                </Table>
                </div>
            
            :
            <div>
                <br></br>
                <Container className='profile_container'>
                    <div className='profile_text'> ??????????????? ?????? ???????????? ????????? ???????????????.</div>
                    <div className='profile_div'></div>
                    <Button className='profile_login' href="/login">?????????</Button>
                </Container>
            </div>  
            }
            </div>
            
        </>
        
    )
}

export default Profile;
