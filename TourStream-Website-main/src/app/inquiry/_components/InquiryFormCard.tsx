"use client";

import RequiredMark from "@/components/layout/RequiredMark";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import {
  Form,
  FormControl,
  FormDescription,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { zodResolver } from "@hookform/resolvers/zod";
import { useCallback } from "react";
import { useForm } from "react-hook-form";
import { z } from "zod";
import axios from "axios";

const introductionInquirySchema = z.object({
  companyName: z.string().min(1, "상호명을 입력해 주세요."),
  representativeName: z.string().min(1, "대표자명을 입력해 주세요."),
  requesterName: z.string().min(1, "신청자명을 입력해 주세요."),
  phoneNumber: z
    .string()
    .regex(
      /^[0-9]{3}-[0-9]{4}-[0-9]{4}$/,
      "유효하지 않은 전화번호 형식입니다."
    ),
  email: z.string().min(1, "이메일 주소를 입력해 주세요."),
  content: z.string().min(1, "문의사항을 입력해 주세요."),
});

export default function InquiryFormCard() {
  const form = useForm<z.infer<typeof introductionInquirySchema>>({
    resolver: zodResolver(introductionInquirySchema),
    defaultValues: {
      companyName: "",
      representativeName: "",
      requesterName: "",
      phoneNumber: "",
      email: "",
      content: "",
    },
  });

  const { handleSubmit } = form;

  const onSubmit = handleSubmit(async (data) => {
    try {
      await axios.post("https://api.tourstream.co.kr/contacts", data);
      alert("문의사항이 등록되었습니다");
    } catch (error) {
      console.error(error)
      alert("문의 등록중 오류가 발생했습니다");
    }
  });

  return (
    <Card className="w-full shadow-lg">
      <CardHeader>
        <CardTitle className="text-lg">도입 문의</CardTitle>
      </CardHeader>
      <CardContent>
        <Form {...form}>
          <form onSubmit={onSubmit} className="space-y-5">
            <FormField
              control={form.control}
              name="companyName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    상호명
                  </FormLabel>
                  <FormControl>
                    <Input {...field} required />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="representativeName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    대표자 이름
                  </FormLabel>
                  <FormControl>
                    <Input {...field} required />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="requesterName"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    신청자 이름
                  </FormLabel>
                  <FormControl>
                    <Input {...field} required />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="phoneNumber"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    전화번호
                  </FormLabel>
                  <FormDescription>
                    문의 답변을 받을 유효한 전화번호를 입력해 주세요
                  </FormDescription>
                  <FormControl>
                    <Input
                      type="tel"
                      {...field}
                      placeholder="010-XXXX-XXXX"
                      pattern="[0-9]{3}-[0-9]{4}-[0-9]{4}"
                      title="전화번호 형식이 올바르지 않습니다. 형식: 010-1234-1234"
                      required
                    />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="email"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    이메일
                  </FormLabel>
                  <FormDescription>
                    문의 답변을 받을 유효한 이메일 주소를 입력해 주세요
                  </FormDescription>
                  <FormControl>
                    <Input type="email" {...field} required />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />
            <FormField
              control={form.control}
              name="content"
              render={({ field }) => (
                <FormItem>
                  <FormLabel>
                    <RequiredMark />
                    문의 내용
                  </FormLabel>
                  <FormControl>
                    <Textarea className="min-h-[120px]" {...field} required />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              )}
            />

            <Button className="w-full text-lg py-6" size="lg">
              문의 제출
            </Button>
          </form>
        </Form>
      </CardContent>
    </Card>
  );
}
