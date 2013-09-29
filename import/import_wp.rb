# gem install xml-simple
require 'xmlsimple'
require 'fileutils.rb'

def parseDate(date) 
	return date.split[0]
end

def parsePost(post) 
	post = post.gsub('http://www.ken-soft.com/doc/', '/pdf/')
	post = post.gsub('http://www.ken-soft.com/', '/')
	['php', 'java', 'asm', 'html', 'c', 'c++', 'cpp', 'CPP' 'js', 'javascript', 'ruby'].each { |l|

		post = post.gsub('<pre  lang="' + l + '" line="1">', "\n```" + l)
		post = post.gsub('<pre lang="' + l + '" line="1">', "\n```" + l)
	}
	post = post.gsub('</pre>', "\n```")

end

# {"domain"=>"post_tag", "nicename"=>"cell-phone", "content"=>"Cell Phone"}
def getTags(obj) 
	tags = ''
	obj.each { |o|
		if o['domain'] == 'post_tag'
			tags += o['content'] + ', '
		end
	}
	tags = tags.chomp(', ')
	return tags.downcase
end

def readXml(file)
	xml = XmlSimple.xml_in(file)
	return xml['channel'][0]['item']
end

# delete old files
FileUtils.rm_rf(Dir.glob('gen/*.markdown'))

posts = readXml('ken-soft.wordpress.2013-09-29.xml')
posts.each { |p|
	# clean data
	title = p['title'][0]
	postName = p['post_name'][0]
	date = parseDate(p['post_date'][0])
	post = parsePost(p['encoded'][0])
	tags = getTags(p['category'])

	# write out file
	File.open('gen/' + date + '-' + postName + '.markdown', 'w') { |file| 
		file.write("---\n") 
		file.write("title: " + title + "\n")
		file.write("author: Kenny Cason\n")
		file.write("tags: " + tags + "\n")
		file.write("---\n\n");
		file.write(post);
	}
}

