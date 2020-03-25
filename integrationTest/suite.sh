#!/bin/bash
set -e

action_pattern='action="([^"]+)"'
ticket_pattern='Location: .*\?ticket=(ST-[-A-Za-z0-9_.=]+)'

get_ticket() {
    login_response=$(curl --fail --silent -c /tmp/cookies http://localhost:8080/auth/realms/master/protocol/cas/login?service=http://localhost)
    if [[ !($login_response =~ $action_pattern) ]] ; then
        echo "Could not parse login form in response"
        echo $login_response
        exit 1
    fi

    login_url=${BASH_REMATCH[1]//&amp;/&}
	echo "login_url => $login_url" 
 	redirect_response=$(curl --fail --silent -D - -b /tmp/cookies -c /tmp/cookies -X POST --data 'username=admin&password=admin&credentialId=' "$login_url")
    #echo "redirect_response => $redirect_response"
    if [[ !($redirect_response =~ $ticket_pattern) ]] ; then
        echo "No service ticket found in response"
        echo $redirect_response
        exit 1
    fi

    ticket=${BASH_REMATCH[1]}
	echo "ticket => $ticket"
}

get_ticket
echo "============"
curl --fail --silent "http://localhost:8080/auth/realms/master/protocol/cas/validate?service=http://localhost&ticket=$ticket"
curl --silent -b /tmp/cookies "http://localhost:8080/auth/realms/master/protocol/cas/logout?service=http://localhost"
echo "============"

get_ticket
echo "============"
curl --fail --silent "http://localhost:8080/auth/realms/master/protocol/cas/serviceValidate?service=http://localhost&format=XML&ticket=$ticket"
curl --silent -b /tmp/cookies "http://localhost:8080/auth/realms/master/protocol/cas/logout?service=http://localhost"
echo "============"

get_ticket
echo "============"
curl --fail --silent "http://localhost:8080/auth/realms/master/protocol/cas/serviceValidate?service=http://localhost&format=JSON&ticket=$ticket"
curl --silent -b /tmp/cookies "http://localhost:8080/auth/realms/master/protocol/cas/logout?service=http://localhost"
echo "============"

get_ticket
echo "============"
curl --fail --silent "http://localhost:8080/auth/realms/master/protocol/cas/p3/serviceValidate?service=http://localhost&format=JSON&ticket=$ticket"
curl --silent -b /tmp/cookies "http://localhost:8080/auth/realms/master/protocol/cas/logout?service=http://localhost"
echo "============"
